package cn.lanehub.ai.examples.customProcessor;

import cn.lanehub.ai.annotation.Order;
import cn.lanehub.ai.brain.IThinkProcessor;
import cn.lanehub.ai.brain.model.ThinkResult;
import cn.lanehub.ai.model.Role;
import cn.lanehub.ai.wizards.model.MagicChat;
import cn.lanehub.ai.wizards.model.MagicMessage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(1)
public class DrawProcessor implements IThinkProcessor {


    @Override
    public ThinkResult process(MagicChat magicChat) {
        String drawCommand =  this.getDrawCommand(magicChat.getLatestMessage());

        if(drawCommand != null){
            InputStream inputStream = new ByteArrayInputStream(drawCommand.getBytes());
            // 处理完毕后无需主脑再处理
            return new ThinkResult(false, inputStream);
        }

        return new ThinkResult();
    }

    @Override
    public String parseChunk(String chunk) {
        return chunk;
    }


    private String getDrawCommand(MagicMessage magicMessage){

        if(!Role.SYSTEM.getValue().equals(magicMessage.getRole())){
            return null;
        }

        String sysResponse = extractContent(magicMessage.getContent()).trim();
        if(sysResponse.startsWith("DRAW")){
            return sysResponse;
        }

        return null;
    }

    private String extractContent(String message) {
        String regex = "#S#(.*?)#E#";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

}
