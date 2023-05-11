package cn.lanehub.ai.core.call.collector.impl;

import cn.lanehub.ai.core.call.CallSpell;
import cn.lanehub.ai.core.call.LocalCallSpell;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过Swagger注册
 */
public class SwaggerCallSpellCollector extends AbstractSpellCollector {


    @Override
    protected List<LocalCallSpell> retrieveCallSpells() {
        List<LocalCallSpell> callSpells = new ArrayList<LocalCallSpell>();
        // TODO
        return callSpells;
    }


}
