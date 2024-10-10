package com.magicvector.ai.executors.impl;

import com.magicvector.ai.core.Spell;
import com.magicvector.ai.executors.ITask;
import com.magicvector.ai.model.Arg;

import java.util.List;

public class Task implements ITask {

    private Spell spell;
    private List<String> argValues;

    public Task(Spell spell, List<String> argValues){
        this.spell = spell;
        this.argValues = argValues;
    }

    @Override
    public Spell getSpell() {
        return this.spell;
    }


    public List<Arg> getParamArgs(){
        return spell.getGptFeedArgs();
    }

    @Override
    public List<String> getArgValues() {
        return argValues;
    }
}
