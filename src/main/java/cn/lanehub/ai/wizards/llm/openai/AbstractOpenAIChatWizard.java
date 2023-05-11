package cn.lanehub.ai.wizards.llm.openai;

import cn.lanehub.ai.exceptions.Assert;
import cn.lanehub.ai.exceptions.MessageStreamException;
import cn.lanehub.ai.exceptions.RemoteLLMCallException;
import cn.lanehub.ai.prompts.IPrompt;
import cn.lanehub.ai.util.StringUtil;
import cn.lanehub.ai.wizards.llm.impl.AbstractChatWizard;
import cn.lanehub.ai.wizards.llm.openai.model.GPTMessage;
import cn.lanehub.ai.wizards.llm.openai.model.GPTRequest;
import cn.lanehub.ai.wizards.model.MagicChat;
import cn.lanehub.ai.wizards.model.MagicMessage;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tbwork.anole.loader.Anole;
import org.tbwork.anole.loader.util.JSON;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class AbstractOpenAIChatWizard extends AbstractChatWizard {

    private static final Logger logger = LoggerFactory.getLogger(GPT4ChatWizard.class);

    private static final  String DEFAULT_CHAT_API_HOST = Anole.getProperty("magicgpt.config.llm.api.openai.chat.url.default");
    public  static final  String OEPNAI_CHAT_API_URL = Anole.getProperty("magicgpt.config.llm.api.openai.chat.url.mirror", DEFAULT_CHAT_API_HOST);

    public static final Long TIME_OUT = 300L;

    private String modelName;

    public AbstractOpenAIChatWizard(String aiName, int maxMessageTokenCount, int maxChatTokenCount, String modelName) {
        super(
                aiName,
                maxMessageTokenCount,
                maxChatTokenCount
        );
        this.modelName = modelName;
    }


    @Override
    protected InputStream doGenerate(MagicChat magicChat) {

        Response response = this.getOpenAIResponse(magicChat);

        InputStream openAiSdkResponseStream = response.body().byteStream();

        return openAiSdkResponseStream;
    }



    @Override
    public IPrompt getSystemPrompt() {
        return getMagicBook().getFirstSystemPrompt();
    }

    @Override
    public String parseChunk(String chunk) {
        logger.debug("ChunkData : {}", chunk);
        if(!chunk.startsWith("data:")){
            throw new MessageStreamException("Fail to parse GPT4 API's steam response. Chunk data:"+chunk);
        }

        chunk = chunk.replace("data:", "").trim();
        if(chunk.equals("[DONE]")){
            return "";
        }
        JsonObject jsonObject = (JsonObject) JSON.parse(chunk);
        try{
            if(jsonObject.getAsJsonArray("choices").get(0).getAsJsonObject().get("delta").getAsJsonObject().get("content") == null){
                // 第一行 或者最后一行，无需摘录
                return "";
            }
            return jsonObject.getAsJsonArray("choices").get(0).getAsJsonObject().get("delta").getAsJsonObject().get("content").getAsString();
        }
        catch (Exception e){
            logger.error("Fail to parse GPT4 API's steam response. Chunk data: {} ", chunk);
            throw new MessageStreamException("Fail to parse GPT4 API's steam response. Chunk data:"+chunk);
        }
    }



    private Response getOpenAIResponse(MagicChat magicChat){
        try {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            //读取超时
            builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
            //连接超时
            builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
            //写入超时
            builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
            //总超时时间
            builder.callTimeout(TIME_OUT, TimeUnit.SECONDS);

            if(Anole.getBoolProperty("magicgpt.config.network.vpn.enabled", false)){
                Proxy vpn = new Proxy(Proxy.Type.HTTP,new InetSocketAddress(Anole.getProperty("magicgpt.config.network.vpn.host"),Anole.getIntProperty("magicgpt.config.network.vpn.port")));
                builder.proxy(vpn);
            }

            OkHttpClient httpClient = builder.build();
            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
            GPTRequest gptRequest = buildChatGPTRequest(magicChat);
            logger.debug(" GPT-API请求体：\n{}",  JSON.toJSONString(gptRequest));
            okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(mediaType, JSON.toJSONString(gptRequest));

            Assert.judge(!StringUtil.isEmpty(Anole.getProperty("OPENAI_API_KEY")), "OPENAI_API_KEY is not set yet.");
            // Create the request
            Request request = new Request.Builder()
                    .url(OEPNAI_CHAT_API_URL)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+Anole.getProperty("OPENAI_API_KEY"))
                    .post(requestBody)
                    .build();

            // Send the request and get the response
            return httpClient.newCall(request).execute();

        } catch (Exception e) {
            throw new RemoteLLMCallException("GPT4");
        }
    }

    /**
     * 构建chatGPT 请求参数
     * @param magicChat
     * @return
     */
    private GPTRequest buildChatGPTRequest(MagicChat magicChat) {
        GPTRequest gptRequest = new GPTRequest();
        gptRequest.setModel(this.modelName);
        gptRequest.setMaxTokens(1000);
        gptRequest.setTemperature(Anole.getDoubleProperty("magicgpt.config.llm.temperature", 0.6));
        gptRequest.setStream(true);
        List<GPTMessage> messages = new ArrayList<>();
        for (MagicMessage magicMessage : magicChat.getChatContent()) {
            GPTMessage GPTMessage = new GPTMessage(magicMessage.getRole(), magicMessage.getContent());
            messages.add(GPTMessage);
        }
        gptRequest.setMessages(messages);
        return gptRequest;
    }


}
