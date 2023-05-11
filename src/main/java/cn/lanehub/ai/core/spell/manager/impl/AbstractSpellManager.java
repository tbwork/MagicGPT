package cn.lanehub.ai.core.spell.manager.impl;

import cn.lanehub.ai.core.call.CallSpell;
import cn.lanehub.ai.core.spell.ISpell;
import cn.lanehub.ai.core.spell.manager.ISpellManager;
import cn.lanehub.ai.exceptions.Assert;
import cn.lanehub.ai.executors.ITask;
import cn.lanehub.ai.executors.call.CallTask;
import cn.lanehub.ai.util.SpellUtil;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractSpellManager implements ISpellManager {


    AbstractSpellManager(String spellName){
        this.spellName = spellName;
    }


    private String spellName;


    @Override
    public String getSpellName() {
        return spellName;
    }

    @Override
    public boolean fit(String spellName) {
        return this.spellName.equals(spellName);
    }


    @Override
    public String castSpell(String spell){

        List<String> spellParts = SpellUtil.getSpellParts(spell);
        spellParts.remove(0);

        return this.doCastSpell(spellParts);

    }

    protected abstract String doCastSpell(List<String> spellArgs);


}
