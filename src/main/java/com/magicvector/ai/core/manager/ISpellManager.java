package com.magicvector.ai.core.manager;

import com.magicvector.ai.core.Spell;

import java.util.List;

public interface ISpellManager {

    String execSpell(String spell);


    void registerSpell(Spell spell);


    List<Spell> getSpellList();


}
