package com.magicvector.ai.brain.llm.openai.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class GPTMessage implements Serializable {
    private String role;
    private String content;
}
