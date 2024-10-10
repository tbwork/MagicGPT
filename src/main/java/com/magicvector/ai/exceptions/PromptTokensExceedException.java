package com.magicvector.ai.exceptions;

import com.magicvector.ai.util.StringUtil;

public class PromptTokensExceedException  extends RuntimeException{



    public PromptTokensExceedException(int chatTokenCount, int maxTokenCount){
        super(StringUtil.formatString("对话中的tokens数量（{}）超过了最大限制{}", chatTokenCount+"", maxTokenCount+""));
    }

}
