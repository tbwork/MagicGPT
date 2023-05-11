package cn.lanehub.ai.core.call.collector.impl;

import cn.lanehub.ai.core.call.CallSpell;
import cn.lanehub.ai.core.call.LocalCallSpell;
import cn.lanehub.ai.core.call.collector.ICallSpellCollector;
import cn.lanehub.ai.core.spell.book.IMagicSpellBook;
import cn.lanehub.ai.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tbwork.anole.loader.util.JSON;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSpellCollector implements ICallSpellCollector {


    private static final Logger logger = LoggerFactory.getLogger(AbstractSpellCollector.class);

    @Override
    public List<LocalCallSpell> collect() {

        List<LocalCallSpell> collectedCallSpells =  this.retrieveCallSpells();

        List<LocalCallSpell> validList = new ArrayList<>();

        for(LocalCallSpell item : collectedCallSpells){

            boolean isValid = this.checkCallSpell(item);

            if(isValid){
                validList.add(item);
            }
            else{
                logger.warn("The call spell {} is not valid, it has been removed, please correct it and try again. Details: ", JSON.toJSONString(item,true));
            }

        }

        return validList;
    }


    private boolean checkCallSpell(LocalCallSpell localCallSpell){

        if(StringUtil.isEmpty(localCallSpell.getInnerSpell().getApiName())){
            return false;
        }

        if(StringUtil.isEmpty(localCallSpell.getInnerSpell().getDescription())){
            return false;
        }

        return true;
    }

    protected abstract List<LocalCallSpell> retrieveCallSpells();
}
