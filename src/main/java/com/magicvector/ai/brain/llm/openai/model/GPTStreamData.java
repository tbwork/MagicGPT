package com.magicvector.ai.brain.llm.openai.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GPTStreamData implements Serializable {
    private String id;
    private String object;
    private Long created;
    private String model;
    private List<GPTStreamDataChoice> choices;
}
