package com.magicvector.ai.wizards.impl;

import com.github.tbwork.anole.loader.util.JSON;
import com.google.gson.JsonObject;
import com.magicvector.ai.brain.Brain;
import com.magicvector.ai.core.Spell;
import com.magicvector.ai.core.manager.ISpellManager;
import com.magicvector.ai.core.manager.impl.SpellManager;
import com.magicvector.ai.exceptions.AIBusyException;
import com.magicvector.ai.exceptions.MessageStreamException;
import com.magicvector.ai.model.Role;
import com.magicvector.ai.model.WizardStatus;
import com.magicvector.ai.prompts.IPrompt;
import com.magicvector.ai.prompts.Language;
import com.magicvector.ai.prompts.impl.SpellPrompt;
import com.magicvector.ai.prompts.impl.SystemPrompt;
import com.magicvector.ai.util.IOUtil;
import com.magicvector.ai.util.SpellUtil;
import com.magicvector.ai.util.StringUtil;
import com.magicvector.ai.wizards.IChatWizard;
import com.magicvector.ai.wizards.model.CustomPrompt;
import com.magicvector.ai.wizards.model.MagicChat;
import com.magicvector.ai.wizards.model.MagicMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * Core.
 */
public class ChatWizard implements IChatWizard {

    private static final Logger logger = LoggerFactory.getLogger(ChatWizard.class);

    private static final String spellQuote = "@#%";

    private static final String spellQuoteFirstChar = spellQuote.charAt(0) +"";

    private Brain brain;

    private Integer maxRounds;

    private static final ISpellManager spellManager = new SpellManager();

    private static final String PARSE_ERROR = "解析AI流式返回时出错，请联系系统管理员。";


    public ChatWizard(Brain brain, Integer maxRounds){
        this.brain = brain;
        this.maxRounds = maxRounds;
    }

    public ChatWizard(Brain brain){
        this.brain = brain;
        this.maxRounds = 20;
    }

    @Override
    public MagicChat startChat(CustomPrompt customPrompt, Language language){
        // 生成system角色提示词。
        IPrompt firstSystemPrompt = this.getSystemPrompt();
        String systemPrompt =  customPrompt.getHeadPrompt() + "\n" + firstSystemPrompt.getPrompt() + customPrompt.getTailPrompt() + "\n" + StringUtil.formatString("请使用{}回答问题", language.getChinese()) ;
        logger.info("系统提示词如下: \n{}", systemPrompt);
        // 传入system提示词
        MagicChat magicChat = new MagicChat(language);
        magicChat.appendSystemMessage(systemPrompt);
        return magicChat;
    }

    @Override
    public void executeSpells(MagicChat magicChat, List<String> spellTexts){

        WizardStatus statusStore = magicChat.getWizardStatus();
        // 进入念咒语的阶段
        magicChat.setStatus(WizardStatus.SPELLING);

        if(spellTexts.isEmpty()){
            return;
        }

        int p =1;
        StringBuilder sb = new StringBuilder();
        for(String spellText:spellTexts){
            sb.append("["+ p++ +"] ");
            logger.debug("AI 咒语：{}", spellText);
            String spellResult = spellManager.execSpell(spellText);
            sb.append(spellResult).append("\n");
        }

        String spellResultText =  "#S# "  +  sb.toString() +  " #E#";
        logger.debug("{}:{}", "咒语执行成功, 返回为", spellResultText);

        magicChat.appendMessage(new MagicMessage("system", spellResultText));

        logger.debug(JSON.toJSONString(magicChat.getChatContent()));
        //  恢复进来时的状态。
        magicChat.setStatus(statusStore);

    }



    @Override
    public void doThink(MagicChat chat, OutputStream outputStream) {
        // 如果AI在忙，报错
        if(!chat.isIdle()){
            throw new AIBusyException(chat.getWizardStatus());
        }

        int p = 0;
        while( p++ < maxRounds){
            try {
                // 大脑的输出就是这里的输入流
                InputStream inputStream = brain.process(chat);
                chat.setStatus(WizardStatus.RESPONDING);
                String aiResponse = processResponseStream(inputStream, outputStream);
                chat.appendMessage(Role.ASSISTANT, aiResponse);
                if(!aiResponse.startsWith(spellQuote)){
                    outputStream.close();
                    chat.setStatus(WizardStatus.IDLE);
                    return;
                }

                /*
                  如果输出为咒语：
                  1. 执行咒语(获得结果并放入Chat中)
                  2. 重新调用generate获取最新结果, outputstream不变
                 */
                List<String> spells = SpellUtil.findSpells(aiResponse);
                for (String spell : spells) {
                    logger.info(spell);
                }
                executeSpells(chat, spells);
            } catch (Exception e) {
                throw new MessageStreamException("AI回答加工处理失败，原因：" + e.getMessage());
            }
        }


    }


    @Override
    public IPrompt getSystemPrompt() {
        List<Spell> spells = spellManager.getSpellList();
        SpellPrompt [] spellPrompts = new SpellPrompt[spells.size()];
        int p = 0;
        for (Spell spell : spells) {
            spellPrompts[p++] = new SpellPrompt(spell);
        }
        return new SystemPrompt(spellPrompts);
    }




    /**
     * 处理AI的输出流（对这里来说是输入流）
     * @param aiResponseStream AI大脑输出的流
     * @param outputStream 指定的输出流
     * @return 如果是咒语，那就是咒语文本；否则返回AI的回答内容。
     * @throws IOException
     */
    private String processResponseStream(InputStream aiResponseStream, OutputStream outputStream) throws IOException {
        StringBuilder spellBuffer = new StringBuilder();
        StringBuilder responseBuffer = new StringBuilder();

        if(aiResponseStream != null){
            // 仅当当前的脑处理器有内容输出才需要处理，没有输出也是可以的。
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(aiResponseStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if(line.isEmpty()){
                    continue;
                }
                if(this.isErrorResponse(line)){
                    this.readAndLogError(bufferedReader);
                    IOUtil.writeToOutpuStream(PARSE_ERROR, outputStream);
                    break;
                }
                String chunkText = brain.parseChunk(line);
                responseBuffer.append(chunkText);
                if(chunkText.startsWith(spellQuoteFirstChar) || !spellBuffer.toString().isEmpty()){
                    // 采集前几个字符确认是否为咒语
                    spellBuffer.append(chunkText);
                    // 咒语采集和处理
                    if(spellBuffer.length() >= spellQuote.length() && !spellBuffer.toString().startsWith(spellQuote)){
                        // 代表不是咒语，直接放过。
                        IOUtil.writeToOutpuStream(spellBuffer.toString(), outputStream);
                        spellBuffer = new StringBuilder();
                    }
                }
                else if(!chunkText.isEmpty()){
                    IOUtil.writeToOutpuStream(chunkText, outputStream);
                }
            }

        }
        return responseBuffer.toString();
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
                logger.error("Failed to call OpenAI API. Details: {}:{}", code, message);
            }
        }
        catch (Exception e){
            logger.error("Fails to read gpt api's error message. Details: {}", e);
        }

    }



}
