package cn.lanehub.ai.brain.model;

import cn.lanehub.ai.brain.IThinkProcessor;
import lombok.Data;

@Data
public class CustomBrainProcessor{


    private IThinkProcessor thinkProcessor;

    private int order;


}
