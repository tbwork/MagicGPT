package com.magicvector.ai.brain.llm.openai.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GPTResponse implements Serializable {
    private String id;
    private String object;
    private Long created;
    private String model;
    private GPTUsage usage;
    private List<GPTChoice> choices;
}
