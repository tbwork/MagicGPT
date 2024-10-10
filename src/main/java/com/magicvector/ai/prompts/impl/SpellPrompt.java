package com.magicvector.ai.prompts.impl;

import com.magicvector.ai.core.Spell;
import com.magicvector.ai.model.Arg;
import com.magicvector.ai.prompts.IPrompt;
import com.magicvector.ai.util.PromptUtil;

/**
 * 每个调用咒语实例的提示词：根据callSpell对象生成
 */
public class SpellPrompt implements IPrompt {

    private String prompt;

    public SpellPrompt(Spell spell){
        this.prompt = generatePrompt(spell);
    }


    @Override
    public String getPrompt() {
        return prompt;
    }

    private String generatePrompt(Spell spell){
        StringBuilder sb = new StringBuilder();
        sb.append(spell.getApiName()).append(" ");
        if(spell.getGptFeedArgs()!=null){
            for(Arg arg : spell.getGptFeedArgs()){
                sb.append(PromptUtil.argToPrompt(arg)).append(" ");
            }
        }
        sb.append("\n").append("咒语功能：").append(spell.getDescription());
        return sb.toString();
    }




}
