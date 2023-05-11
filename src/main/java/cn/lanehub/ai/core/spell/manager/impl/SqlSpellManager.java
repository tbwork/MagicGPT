package cn.lanehub.ai.core.spell.manager.impl;

import cn.lanehub.ai.core.spell.ISpell;
import cn.lanehub.ai.core.spell.manager.ISpellManager;
import cn.lanehub.ai.prompts.IPrompt;

import java.util.List;

public class SqlSpellManager extends AbstractSpellManager {

    public SqlSpellManager() {
        super("sql");
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
        return null;
    }
}
