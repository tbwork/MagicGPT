package com.magicvector.ai.brain.llm.openai.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class GPTChoice implements Serializable {
    private GPTMessage message;
    @SerializedName("finish_reason")
    private String finishReason;
    private Integer index;
}
