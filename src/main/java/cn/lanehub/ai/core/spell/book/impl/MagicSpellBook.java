package cn.lanehub.ai.core.spell.book.impl;

import cn.lanehub.ai.core.spell.ISpell;
import cn.lanehub.ai.core.spell.manager.ISpellManager;
import cn.lanehub.ai.core.spell.manager.impl.*;
import cn.lanehub.ai.core.spell.book.IMagicSpellBook;
import cn.lanehub.ai.exceptions.Assert;
import cn.lanehub.ai.exceptions.SpellNotFoundException;
import cn.lanehub.ai.model.SpellType;
import cn.lanehub.ai.prompts.IPrompt;
import cn.lanehub.ai.prompts.impl.FirstSystemPrompt;
import cn.lanehub.ai.util.SpellUtil;

import java.util.*;

public class MagicSpellBook implements IMagicSpellBook {


    private volatile int spellCategoryCount = 0;
    private volatile List<SpellType> alreadyRegisteredTypeList;

    private Map<SpellType, ISpellManager> supportedMagicSpellManagers;

    public MagicSpellBook(){
        supportedMagicSpellManagers = new HashMap<SpellType, ISpellManager>();
        supportedMagicSpellManagers.put(SpellType.CALL, new CallSpellManager());
        supportedMagicSpellManagers.put(SpellType.SEARCH, new SearchSpellManager());
        supportedMagicSpellManagers.put(SpellType.VIEW,ViewSpellManager.INSTANCE);
        supportedMagicSpellManagers.put(SpellType.SQL,new SqlSpellManager());
        supportedMagicSpellManagers.put(SpellType.MATCH,new MatchSpellManager());
        alreadyRegisteredTypeList = new ArrayList<SpellType>();
    }



    @Override
    public ISpellManager resolve(String spellText) {

        List<String> spellParts = SpellUtil.getSpellParts(spellText);

        String spellName = spellParts.get(0).toLowerCase();
        Assert.isNotBlank(spellName, "Spell name");

        for (ISpellManager spellManager : supportedMagicSpellManagers.values()){
            if(spellManager.fit(spellName)){
                return spellManager;
            }
        }

        throw new SpellNotFoundException(spellParts.get(0));
    }

    @Override
    public void register(ISpell spell) {
         for(ISpellManager spellManager: supportedMagicSpellManagers.values()){
            if(spellManager.fit((spell.getType().getName()))){
                spellManager.registerSpell(spell);
                if(!alreadyRegisteredTypeList.contains(spell.getType())){
                    alreadyRegisteredTypeList.add(spell.getType());
                }
                return;
            }
         }
    }

    @Override
    public FirstSystemPrompt getFirstSystemPrompt() {

        List<IPrompt> spellTypePrompts = new ArrayList<>();

        // 根据获取所有注册的咒语类型，逐个注册
        for(int i = 0 ; i < alreadyRegisteredTypeList.size();i++){
            SpellType spellType =  alreadyRegisteredTypeList.get(i);
            ISpellManager spellManager = supportedMagicSpellManagers.get(spellType);
            IPrompt spellPrompt = spellManager.generateSpellPrompt(i+1);
            if(spellPrompt != null){
                spellTypePrompts.add(spellPrompt);
            }
        }

        return new FirstSystemPrompt(spellTypePrompts.toArray(new IPrompt[0]));
    }


}
