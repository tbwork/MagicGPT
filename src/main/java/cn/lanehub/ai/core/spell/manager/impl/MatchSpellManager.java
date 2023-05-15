package cn.lanehub.ai.core.spell.manager.impl;

import cn.lanehub.ai.core.spell.ISpell;
import cn.lanehub.ai.prompts.IPrompt;

import java.util.List;

public class MatchSpellManager extends AbstractSpellManager {



    public MatchSpellManager() {
        super("match");
    }


    @Override
    public void registerSpell(ISpell spell) {

    }

    @Override
    public List<ISpell> getSpellList() {
        return null;
    }

    @Override
    public IPrompt generateSpellPrompt(int th) {
        return null;
    }


    @Override
    protected String doCastSpell(List<String> spellArgs) {


        String vbName = spellArgs.get(0);
        String queryText = spellArgs.get(1);



        return null;
    }
}
