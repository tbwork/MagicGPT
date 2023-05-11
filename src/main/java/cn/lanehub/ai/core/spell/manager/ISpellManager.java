package cn.lanehub.ai.core.spell.manager;

import cn.lanehub.ai.core.spell.ISpell;
import cn.lanehub.ai.prompts.IPrompt;

import java.util.List;

public interface ISpellManager {

    String getSpellName();


    boolean fit(String spellName);


    String castSpell(String spell);


    void registerSpell(ISpell spell);


    List<ISpell> getSpellList();


    IPrompt generateSpellPrompt(int th);


}
