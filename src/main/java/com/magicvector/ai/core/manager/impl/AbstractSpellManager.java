package com.magicvector.ai.core.manager.impl;

import com.magicvector.ai.core.manager.ISpellManager;
import com.magicvector.ai.util.SpellUtil;

import java.util.List;

public abstract class AbstractSpellManager implements ISpellManager {


    @Override
    public String execSpell(String spell){

        List<String> spellParts = SpellUtil.getSpellParts(spell);

        return this.doExecSpell(spellParts);

    }

    protected abstract String doExecSpell(List<String> spellParts);


}
