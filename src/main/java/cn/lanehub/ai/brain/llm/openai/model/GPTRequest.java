package cn.lanehub.ai.brain.llm.openai.model;
 
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yangheng
 * @date 2023/3/30 18:18
 */
@Data
public class GPTRequest implements Serializable {
    private String model;
    @SerializedName("max_tokens")
    private Integer maxTokens;
    private Double temperature;
    private List<GPTMessage> messages;
    private Boolean stream;
}
