package cn.lanehub.ai.prompts.impl;

import cn.lanehub.ai.prompts.IPrompt;

public class NumberPrompt implements IPrompt {

    private int number;

    public NumberPrompt(int number){
        this.number  = number;
    }


    @Override
    public String getPrompt() {
        return number+"";
    }
}
