package com.magicvector.ai.prompts.impl;

import com.magicvector.ai.prompts.IPrompt;
import com.magicvector.ai.util.PromptUtil;
import com.magicvector.ai.util.StringUtil;

public class SystemPrompt extends AbstractFixTemplatePrompt {

    public SystemPrompt(IPrompt... prompts){
        super("System", prompts);
    }

    @Override
    protected String doGeneratePrompt(String template, String... promptTexts) {
        String spellPromptText = PromptUtil.joinPrompt(promptTexts, "\n\n");
        return StringUtil.formatString(template, spellPromptText);
    }

}
