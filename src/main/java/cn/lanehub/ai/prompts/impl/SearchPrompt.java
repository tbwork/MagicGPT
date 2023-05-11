package cn.lanehub.ai.prompts.impl;

import cn.lanehub.ai.prompts.IPrompt;
import cn.lanehub.ai.util.ArrayUtil;
import cn.lanehub.ai.util.PromptUtil;

public class SearchPrompt extends AbstractSpellCategoryTemplatePrompt{

    private String prompt;


    public SearchPrompt(int th, IPrompt ... prompts) {
        super(th, "SearchSpell", prompts);
    }

    @Override
    protected String doGeneratePrompt(String template, String... promptTexts) {
        String [] supportedEngineNames = ArrayUtil.subArray(promptTexts, 1, promptTexts.length);
        return PromptUtil.formatPrompt(template, promptTexts[0], PromptUtil.join(supportedEngineNames, ","));
    }


}
