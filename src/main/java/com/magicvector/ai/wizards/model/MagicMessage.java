package com.magicvector.ai.wizards.model;

import com.magicvector.ai.util.MessageUtil;
import lombok.Data;

@Data
public class MagicMessage {

    private String role;

    private String content;

    private int tokenCount;

    public MagicMessage(String role, String content){
        this.role = role;
        this.content = content;
        this.tokenCount = MessageUtil.countTokens(content);
    }

    public MagicMessage(){
        this.role = null;
        this.content = "";
        this.tokenCount = 0;
    }

}
