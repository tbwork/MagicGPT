package cn.lanehub.ai.prompts.impl;

import cn.lanehub.ai.prompts.IPrompt;
import cn.lanehub.ai.util.PromptUtil;

public class ViewPrompt extends AbstractSpellCategoryTemplatePrompt{
    public ViewPrompt(int th, IPrompt... prompts) {
        super(th, "ViewSpell", prompts);
    }

    @Override
    protected String doGeneratePrompt(String template, String... promptTexts) {
        return PromptUtil.formatPrompt(template, promptTexts[0]);
    }
}
