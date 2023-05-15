package cn.lanehub.ai.prompts.impl;

import cn.lanehub.ai.prompts.IPrompt;
import cn.lanehub.ai.util.PromptUtil;
import cn.lanehub.ai.util.StringUtil;

public class CallPrompt extends AbstractSpellCategoryTemplatePrompt {

    public CallPrompt(int th, IPrompt... prompts){
        super(th, "CallSpell", prompts);
    }

    @Override
    protected String doGeneratePrompt(String template, String... promptTexts) {

        StringBuilder callSubSpellsSb = new StringBuilder();

        for(int i = 1 ; i < promptTexts.length ; i++){
            callSubSpellsSb.append( "1."+i+ " ")
                    .append(promptTexts[i])
                    .append("\n");
        }

        String callSubSpellsText = callSubSpellsSb.toString();

        return StringUtil.formatString(template, promptTexts[0], callSubSpellsText);
    }
}
