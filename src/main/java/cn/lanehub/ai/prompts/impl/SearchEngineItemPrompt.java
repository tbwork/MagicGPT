package cn.lanehub.ai.prompts.impl;

import cn.lanehub.ai.core.search.SearchEngineType;
import cn.lanehub.ai.exceptions.Assert;
import cn.lanehub.ai.prompts.IPrompt;
import cn.lanehub.ai.util.PromptUtil;

public class SearchEngineItemPrompt implements IPrompt {

    private String prompt;

    public SearchEngineItemPrompt(String engineName){

        this.prompt = PromptUtil.formatPrompt("{}-{}、", engineName, SearchEngineType.fromValue(engineName).getDescription());
    }

    @Override
    public String getPrompt() {
        return this.prompt;
    }
}
