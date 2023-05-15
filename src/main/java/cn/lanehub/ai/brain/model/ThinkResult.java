package cn.lanehub.ai.brain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.InputStream;

/**
 * 思考结果
 */
@Data
@AllArgsConstructor
public class ThinkResult {

    /**
     * 是否需要进一步思考
     */
    private boolean needFurtherThink;

    /**
     * 大脑的输出流也是后续处理的输入流
     */
    private InputStream brainOutputStream;

    public ThinkResult(InputStream inputStream){
        this.brainOutputStream = inputStream;
        this.needFurtherThink = true;
    }

}
