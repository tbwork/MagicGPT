package cn.lanehub.ai.brain.llm.openai.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yangheng
 * @date 2023/4/28 14:45
 */
@Data
public class GPTChoice implements Serializable {
    private GPTMessage message;
    @SerializedName("finish_reason")
    private String finishReason;
    private Integer index;
}
