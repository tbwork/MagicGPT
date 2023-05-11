package cn.lanehub.ai.core.call.collector;

import cn.lanehub.ai.core.call.CallSpell;
import cn.lanehub.ai.core.call.LocalCallSpell;
import cn.lanehub.ai.core.spell.book.IMagicSpellBook;

import java.util.List;

public interface ICallSpellCollector {


    List<LocalCallSpell> collect();


}
