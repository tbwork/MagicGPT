package cn.lanehub.ai.brain.llm.openai.model;
 
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yangheng
 * @date 2023/4/23 15:40
 */
@Data
public class GPTStreamDataChoice implements Serializable {
    private String index;
    @SerializedName("finish_reason")
    private String finishReason;
    private GPTMessage delta;
}
