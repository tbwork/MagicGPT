package cn.lanehub.ai.core.spell.book;

import cn.lanehub.ai.core.spell.ISpell;
import cn.lanehub.ai.core.spell.manager.ISpellManager;
import cn.lanehub.ai.prompts.impl.FirstSystemPrompt;

/**
 * resolve the spell command and
 */
public interface IMagicSpellBook {


    ISpellManager resolve(String spell);


    void register(ISpell spell);


    FirstSystemPrompt getFirstSystemPrompt();


}
