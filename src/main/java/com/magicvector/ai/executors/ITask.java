package com.magicvector.ai.executors;

import com.magicvector.ai.core.Spell;
import com.magicvector.ai.model.Arg;

import java.util.List;

public interface ITask {

    public Spell getSpell();

    public List<Arg> getParamArgs();

    public List<String> getArgValues();

}
