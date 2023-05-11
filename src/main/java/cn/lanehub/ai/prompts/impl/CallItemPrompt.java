package cn.lanehub.ai.prompts.impl;

import cn.lanehub.ai.core.call.CallSpell;
import cn.lanehub.ai.model.Arg;
import cn.lanehub.ai.prompts.IPrompt;
import cn.lanehub.ai.prompts.Language;
import cn.lanehub.ai.util.PromptUtil;

/**
 * 每个调用咒语实例的提示词：根据callSpell对象生成
 */
public class CallItemPrompt implements IPrompt {

    private String prompt;

    public CallItemPrompt(CallSpell callSpell){
        this.prompt = generatePrompt(callSpell);
    }


    @Override
    public String getPrompt() {
        return prompt;
    }

    private String generatePrompt(CallSpell callSpell){


        StringBuilder sb = new StringBuilder();

        sb.append("call ").append(callSpell.getApiName()).append(" ");

        if(callSpell.getGptFeedArgs()!=null){
            for(Arg arg : callSpell.getGptFeedArgs()){
                sb.append(PromptUtil.argToString(arg)).append(" ");
            }
        }

        sb.append("\n").append("咒语功能：").append(callSpell.getDescription());

        return sb.toString();
    }




}
