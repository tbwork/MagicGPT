package cn.lanehub.ai.brain.llm.openai;

import cn.lanehub.ai.brain.llm.AbstractRemoteLLMThinkProcessor;
import cn.lanehub.ai.brain.llm.openai.model.GPTMessage;
import cn.lanehub.ai.brain.llm.openai.model.GPTRequest;
import cn.lanehub.ai.brain.model.ThinkResult;
import cn.lanehub.ai.exceptions.Assert;
import cn.lanehub.ai.exceptions.MessageStreamException;
import cn.lanehub.ai.exceptions.RemoteLLMCallException;
import cn.lanehub.ai.model.BrainMainProcessorType;
import cn.lanehub.ai.util.StringUtil;
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
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OpenAIThinkProcessor extends AbstractRemoteLLMThinkProcessor {

    private static final Logger logger = LoggerFactory.getLogger(OpenAIThinkProcessor.class);

    private String chatApiUrl;

    public OpenAIThinkProcessor(BrainMainProcessorType brainMainProcessorType) {
        super(brainMainProcessorType,
                Anole.getLongProperty("magicgpt.config.llm.api.openai.timeout.read", 30L),
                Anole.getLongProperty("magicgpt.config.llm.api.openai.timeout.connect", 10L),
                Anole.getLongProperty("magicgpt.config.llm.api.openai.timeout.call", 1000L)
        );
        this.chatApiUrl = this.getChatAPIURL();
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
            logger.error("Fail to parse GPT4 API's steam response. Chunk data: {} ", chunk );
            throw new MessageStreamException("Fail to parse GPT4 API's steam response. Chunk data:" + chunk );
        }
    }

    @Override
    public ThinkResult process(MagicChat magicChat) {

        Response response = this.getOpenAIResponse(magicChat);

        InputStream openAiSdkResponseStream = response.body().byteStream();

        return new ThinkResult(openAiSdkResponseStream);
    }



    private Response getOpenAIResponse(MagicChat magicChat){
        try {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            //读取超时
            builder.readTimeout(this.readTimeoutSeconds, TimeUnit.SECONDS);
            //连接超时
            builder.connectTimeout(this.connectTimeoutSeconds, TimeUnit.SECONDS);
            //总超时时间
            builder.callTimeout(this.callTimeoutSeconds, TimeUnit.SECONDS);

            if(Anole.getBoolProperty("magicgpt.config.network.vpn.enabled", false)){
                Proxy vpn = new Proxy(Proxy.Type.HTTP,new InetSocketAddress(Anole.getProperty("magicgpt.config.network.vpn.host"), Anole.getIntProperty("magicgpt.config.network.vpn.port")));
                builder.proxy(vpn);
            }

            OkHttpClient httpClient = builder.build();
            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
            GPTRequest gptRequest = buildChatGPTRequest(magicChat);
            logger.debug(" GPT-API Request：\n{}",  JSON.toJSONString(gptRequest));
            okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(mediaType, JSON.toJSONString(gptRequest));

            Assert.judge(!StringUtil.isEmpty(Anole.getProperty("OPENAI_API_KEY")), "OPENAI_API_KEY is not set yet.");
            // Create the request
            Request request = new Request.Builder()
                    .url(this.chatApiUrl)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+Anole.getProperty("OPENAI_API_KEY"))
                    .post(requestBody)
                    .build();

            // Send the request and get the response
            return httpClient.newCall(request).execute();

        } catch (Exception e) {
            logger.error("Fails to call OpenAI API. Details: {}", e);
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
        gptRequest.setModel(this.brainMainProcessorType.getValue());
        gptRequest.setMaxTokens(Anole.getIntProperty("magicgpt.config.llm.api.openai.chat.response.max.length", 500));
        gptRequest.setTemperature(Anole.getDoubleProperty("magicgpt.config.llm.api.openai.chat.temperature", 0.6));
        gptRequest.setStream(true);
        List<GPTMessage> messages = new ArrayList<>();
        for (MagicMessage magicMessage : magicChat.getChatContent()) {
            GPTMessage GPTMessage = new GPTMessage(magicMessage.getRole(), magicMessage.getContent());
            messages.add(GPTMessage);
        }
        gptRequest.setMessages(messages);
        return gptRequest;
    }


    private String getChatAPIURL(){

        // 先检查镜像
        String mirror = Anole.getProperty("magicgpt.config.llm.api.openai.chat.url.mirror");
        if( StringUtil.isEmpty(mirror)){
            return Anole.getProperty("magicgpt.config.llm.api.openai.chat.url.default");
        }
        return mirror;
    }
}
