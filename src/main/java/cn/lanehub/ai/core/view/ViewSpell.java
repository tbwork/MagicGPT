package cn.lanehub.ai.core.view;

import cn.lanehub.ai.core.spell.ISpell;
import cn.lanehub.ai.model.SpellType;

public class ViewSpell implements ISpell {

    @Override
    public SpellType getType() {
        return SpellType.VIEW;
    }


}
