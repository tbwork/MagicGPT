package cn.lanehub.ai.brain.llm;

import cn.lanehub.ai.brain.IThinkProcessor;
import cn.lanehub.ai.model.BrainMainProcessorType;

public abstract class AbstractLLMThinkProcessor implements IThinkProcessor {

    protected BrainMainProcessorType brainMainProcessorType;


    public AbstractLLMThinkProcessor(BrainMainProcessorType brainMainProcessorType){
        this.brainMainProcessorType = brainMainProcessorType;
    }



}
