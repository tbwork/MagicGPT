package com.magicvector.ai.brain.llm.openai.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class GPTUsage implements Serializable {
    @SerializedName("prompt_tokens")
    private Integer promptTokens;
    @SerializedName("completion_tokens")
    private Integer completionTokens;
    @SerializedName("total_tokens")
    private Integer totalTokens;
}
