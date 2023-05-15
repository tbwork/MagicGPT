package cn.lanehub.ai.core.match;

import cn.lanehub.ai.core.spell.ISpell;
import cn.lanehub.ai.model.SpellType;

public class MatchSpell implements ISpell {
    @Override
    public SpellType getType() {
        return SpellType.MATCH;
    }
}
