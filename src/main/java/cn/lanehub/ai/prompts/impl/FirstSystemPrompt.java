package cn.lanehub.ai.prompts.impl;

import cn.lanehub.ai.prompts.IPrompt;
import cn.lanehub.ai.util.ArrayUtil;
import cn.lanehub.ai.util.PromptUtil;
import cn.lanehub.ai.util.StringUtil;
import org.tbwork.anole.loader.Anole;

import java.util.Date;

public class FirstSystemPrompt extends AbstractFixTemplatePrompt {


    public FirstSystemPrompt(IPrompt ... prompts){
        super("FirstSystem", prompts);
    }

    @Override
    protected String doGeneratePrompt(String template, String... promptTexts) {

        String spellPromptText = PromptUtil.joinPrompt(promptTexts, "\n");

        return StringUtil.formatString(template, new String[]{spellPromptText});
    }

}
