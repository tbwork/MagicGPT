package cn.lanehub.ai.wizards.readers;

import cn.lanehub.ai.brain.IThinkProcessor;
import cn.lanehub.ai.brain.model.ThinkResult;
import cn.lanehub.ai.exceptions.MessageStreamException;
import cn.lanehub.ai.model.Role;
import cn.lanehub.ai.model.WizardStatus;
import cn.lanehub.ai.util.MessageUtil;
import cn.lanehub.ai.util.NetUtil;
import cn.lanehub.ai.util.SpellUtil;
import cn.lanehub.ai.wizards.IChatWizard;
import cn.lanehub.ai.wizards.model.MagicChat;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tbwork.anole.loader.util.JSON;

import java.io.*;
import java.util.List;

/**
 * 从AI的流式返回中解析字符串，将其inputstream中的数据逐个输出到 messageOutputStream 中。
 * 如果是咒语，则读取完毕后直接执行，将结果放入Chat中，继续推进AI生成，并且绑定新的inputstream流。
 * 新的inputstream
 */
public class AIResponseStreamReadTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(AIResponseStreamReadTask.class);

    private List<IThinkProcessor> brain;

    private BufferedReader bufferedReader;
    private OutputStream outputStream;

    private StringBuilder spellBuffer;

    private StringBuilder responseTextBuffer;

    private MagicChat magicChat;

    private IChatWizard chatWizard;

    private static final String spellQuote = "@#%";

    private static final String spellQuoteFirstChar = spellQuote.charAt(0) +"";



    public AIResponseStreamReadTask(List<IThinkProcessor> brain, OutputStream outputStream, MagicChat magicChat) {
        this.brain = brain;
        this.outputStream = outputStream;
        this.magicChat = magicChat;
        this.spellBuffer = new StringBuilder();
        this.responseTextBuffer = new StringBuilder();
    }

    @Override
    public void run() {
        try {

            // 根据脑中的处理器顺序逐个处理，当无需进一步处理时直接跳出。
            for(int i = 0; i < brain.size(); i ++){
                IThinkProcessor processor = brain.get(i);
                if(!this.outputThinkResult(processor)){
                    break;
                }
            }

            // ----------------------- GPT 输出结束  --------------------------------------------

            //  GPT的回答存放在Chat中
            this.magicChat.appendMessage(Role.ASSISTANT, this.responseTextBuffer.toString());
            if(spellBuffer.toString().trim().isEmpty()){
                // 没有咒语代表本次回答输出结束
                outputStream.close();
                this.magicChat.setStatus(WizardStatus.IDLE);
                return;
            }

            /*
              如果输出为咒语
              1. 执行咒语(获得结果并放入Chat中)
              2. 重新调用generate获取最新结果, outputstream不变
             */
            List<String> spells = SpellUtil.findSpells(spellBuffer.toString());
            chatWizard.executeSpells(magicChat, spells);
            // 再次调用无需考虑Chat的忙闲。
            chatWizard.forceGenerate(magicChat, outputStream);

        } catch (Exception e) {
            throw new MessageStreamException(e);
        } finally {
            clear();
        }
    }



    private boolean isErrorResponse(String firstLine){
        return "{".equals(firstLine.trim());
    }


    private void readAndLogError(BufferedReader bufferedReader){
        try{
            StringBuilder responseTextBuilder = new StringBuilder();
            responseTextBuilder.append("{");
            String line;
            while ((line = bufferedReader.readLine()) != null){
                responseTextBuilder.append(line);
            }

            String error = "未知错误";
            JsonObject errorObj = (JsonObject) JSON.parse(responseTextBuilder.toString());
            if(errorObj.has("error") && (errorObj.getAsJsonObject("error").has("code") || errorObj.getAsJsonObject("error").has("message"))){

                String code = (errorObj.getAsJsonObject("error").get("code")==null ? "" : errorObj.getAsJsonObject("error").get("code")).toString();
                String message = errorObj.getAsJsonObject("error").get("message")==null?"":errorObj.getAsJsonObject("error").get("message").toString();
                logger.error("Failed to call OpenAI API Details: {}:{}", code, message);
            }
        }
        catch (Exception e){
            logger.error("Fails to read gpt api's error message. Details: {}", e);
        }

    }


    private void clear(){
        this.bufferedReader = null;
        this.outputStream = null;
        this.chatWizard = null;
    }


    /**
     * 输出大脑思考的结果内容
     * @param thinkProcessor
     * @return
     * @throws IOException
     */
    private boolean outputThinkResult(IThinkProcessor thinkProcessor) throws IOException {

        ThinkResult thinkResult = thinkProcessor.process(magicChat, outputStream);
        this.bufferedReader = new BufferedReader(new InputStreamReader(thinkResult.getBrainOutputStream()));
        String line;
        this.magicChat.setStatus(WizardStatus.RESPONDING);
        while ((line = bufferedReader.readLine()) != null) {
            if(line.isEmpty()){
                continue;
            }
            if(this.isErrorResponse(line)){
                this.readAndLogError(bufferedReader);
                NetUtil.writeToOutpuStream(MessageUtil.getLLMDownError(this.magicChat.getLanguage()), outputStream);
            }
            String chunkText = thinkProcessor.parseChunk(line);
            this.responseTextBuffer.append(chunkText);
            if(chunkText.startsWith(spellQuoteFirstChar) || !spellBuffer.toString().isEmpty()){
                // 采集前几个字符确认是否为咒语
                this.spellBuffer.append(chunkText);
                // 咒语采集和处理
                if(spellBuffer.length() >= spellQuote.length() && !spellBuffer.toString().startsWith(spellQuote)){
                    // 代表不是咒语，直接放过。
                    NetUtil.writeToOutpuStream(this.spellBuffer.toString(), outputStream);
                    spellBuffer = new StringBuilder();
                }
            }
            else if(!chunkText.isEmpty()){
                NetUtil.writeToOutpuStream(chunkText, outputStream);
            }
        }

        return thinkResult.isNeedFurtherThink();

    }




}
