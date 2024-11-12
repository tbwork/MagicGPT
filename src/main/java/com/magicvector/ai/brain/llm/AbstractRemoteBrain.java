package com.magicvector.ai.brain.llm;

import com.magicvector.ai.brain.Brain;


public abstract class AbstractRemoteBrain implements Brain{


    protected long readTimeoutSeconds;

    protected long connectTimeoutSeconds;

    protected long callTimeoutSeconds;

    public AbstractRemoteBrain(long readTimeoutSeconds, long connectTimeoutSeconds, long callTimeoutSeconds){
        this.readTimeoutSeconds = readTimeoutSeconds;
        this.connectTimeoutSeconds = connectTimeoutSeconds;
        this.callTimeoutSeconds = callTimeoutSeconds;
    }


}
