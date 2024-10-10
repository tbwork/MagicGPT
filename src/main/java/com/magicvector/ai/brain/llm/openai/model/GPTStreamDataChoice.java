package com.magicvector.ai.brain.llm.openai.model;
 
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class GPTStreamDataChoice implements Serializable {
    private String index;
    @SerializedName("finish_reason")
    private String finishReason;
    private GPTMessage delta;
}
