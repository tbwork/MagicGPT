package cn.lanehub.ai.prompts.impl;

import cn.lanehub.ai.prompts.IPrompt;
import cn.lanehub.ai.prompts.Language;
import cn.lanehub.ai.util.PromptUtil;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class AbstractFixTemplatePrompt implements IPrompt {


    protected String prompt;

    public AbstractFixTemplatePrompt(String promptResourceName, IPrompt ... prompts){
        //从配置文件中读取多语言的提示词模板
        this.loadPromptFromResource(promptResourceName);

        //根据模板生成最终提示词
        this.generatePrompt(prompts);
    }


    @Override
    public String getPrompt() {
        return prompt;
    }


    private void loadPromptFromResource(String promptResourceName){

        this.prompt = PromptUtil.parseResource("prompts/"+promptResourceName+".prompt");

    }


    /**
     *
     */
    private void generatePrompt(IPrompt ... prompts){


        String template = this.prompt;

        String [] promptTexts = new String[prompts.length];

        for(int i =0 ; i < prompts.length; i++){
            promptTexts[i] = prompts[i].getPrompt();
        }

        this.prompt = this.doGeneratePrompt(template, promptTexts);

    }


    protected abstract String doGeneratePrompt(String template, String ... promptTexts);
}
