package cn.lanehub.ai.prompts.impl;

import cn.lanehub.ai.prompts.IPrompt;
import cn.lanehub.ai.util.ArrayUtil;

import java.util.Arrays;

public abstract class AbstractSpellCategoryTemplatePrompt extends AbstractFixTemplatePrompt{

    public AbstractSpellCategoryTemplatePrompt(int th, String promptResourceName, IPrompt... prompts) {
        super(promptResourceName, ArrayUtil.insertAtIndex(prompts, 0, new NumberPrompt(th)));
    }


}
