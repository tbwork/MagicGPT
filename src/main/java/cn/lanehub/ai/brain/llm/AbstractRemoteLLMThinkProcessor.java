package cn.lanehub.ai.brain.llm;

import cn.lanehub.ai.model.BrainMainProcessorType;


public abstract class AbstractRemoteLLMThinkProcessor extends AbstractLLMThinkProcessor {


    protected long readTimeoutSeconds;

    protected long connectTimeoutSeconds;

    protected long callTimeoutSeconds;

    public AbstractRemoteLLMThinkProcessor(BrainMainProcessorType brainMainProcessorType, long readTimeoutSeconds, long connectTimeoutSeconds, long callTimeoutSeconds){
        super(brainMainProcessorType);
        this.readTimeoutSeconds = readTimeoutSeconds;
        this.connectTimeoutSeconds = connectTimeoutSeconds;
        this.callTimeoutSeconds = callTimeoutSeconds;
    }


}
