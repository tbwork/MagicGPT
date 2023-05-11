package cn.lanehub.ai.wizards.llm.openai.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yangheng
 * @date 2023/3/30 18:20
 */
@Data
@AllArgsConstructor
public class GPTMessage implements Serializable {
    private String role;
    private String content;
}
