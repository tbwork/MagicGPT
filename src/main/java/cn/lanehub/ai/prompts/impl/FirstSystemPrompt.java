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

        String spellCount = promptTexts.length+"";

        String spellPromptText = PromptUtil.joinPrompt(promptTexts, "\n");

        String [] args = ArrayUtil.insertAtIndex(promptTexts, 0 , spellCount);

        String debugPrompt = Anole.getBoolProperty("magicgpt.debug.enable", false)? Anole.getProperty("magicgpt.debug.prompt")  :"";

        return StringUtil.formatString(template, new String[]{spellCount, spellPromptText, debugPrompt});
    }

}
