package com.magicvector.ai.brain.llm.openai.model;

public enum OpenAIModel {
    GPT3_5_TURBO("gpt-3.5-turbo", 4096, "Most capable GPT-3.5 model and optimized for chat at 1/10th the cost of text-davinci-003. Will be updated with our latest model iteration."),

    GPT3_5_TURBO_0301("gpt-3.5-turbo-0301", 4096, "Snapshot of gpt-3.5-turbo from March 1st 2023. Unlike gpt-3.5-turbo, this model will not receive updates, and will be deprecated 3 months after a new version is released."),

    GPT3_5_TEXT_DAVINCI_003("text-davinci-003", 4096, "Can do any language task with better quality, longer output, and consistent instruction-following than the curie, babbage, or ada models. Also supports inserting completions within text."),

    GPT3_5_TEXT_DAVINCI_002("text-davinci-002", 4096, "Similar capabilities to text-davinci-003 but trained with supervised fine-tuning instead of reinforcement learning."),

    GPT3_5_CODE_DAVINCI_002("code-davinci-002",8001, "Optimized for code-completion tasks."),

    GPT4_O1_MINI("o1-mini", 8192,"More capable than any GPT-3.5 model, able to do more complex tasks, and optimized for chat. Will be updated with our latest model iteration."),

    GPT4_O4_MINI("gpt-4o-mini", 8192,"More capable than any GPT-3.5 model, able to do more complex tasks, and optimized for chat. Will be updated with our latest model iteration."),

    GPT4("gpt-4", 8192,"More capable than any GPT-3.5 model, able to do more complex tasks, and optimized for chat. Will be updated with our latest model iteration."),
    GPT4_0314("gpt-4-0314", 8192, "Snapshot of gpt-4 from March 14th 2023. Unlike gpt-4, this model will not receive updates, and will be deprecated 3 months after a new version is released."),

    GPT4_32K("gpt-4-32k", 32768,"Same capabilities as the base gpt-4 mode but with 4x the context length. Will be updated with our latest model iteration."),

    GPT4_32K_0314("gpt-4-32k-0314", 32768, "Snapshot of gpt-4-32 from March 14th 2023. Unlike gpt-4-32k, this model will not receive updates, and will be deprecated 3 months after a new version is released.");
    private final String value;

    private int maxTokenCount;

    private final String description;

    private OpenAIModel(String value, int maxTokenCount, String description) {
        this.value = value;
        this.description = description;
        this.maxTokenCount = maxTokenCount;
    }

    public String getValue() {
        return value;
    }


    public int getMaxTokenCount(){
        return maxTokenCount;
    }

    public String getDescription() {
        return description;
    }
}
