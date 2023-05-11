package cn.lanehub.ai.core.search;

import cn.lanehub.ai.core.spell.ISpell;
import cn.lanehub.ai.model.SpellType;
import cn.lanehub.ai.network.impl.restful.PostRestfulAccess;

import java.util.ArrayList;
import java.util.List;

public class SearchSpell implements ISpell {


    private List<SearchEngineType> supportedSearchEngineTypes ;


    public SearchSpell(SearchEngineType ... searchEngineTypes){
        this.supportedSearchEngineTypes = new ArrayList<>();
        for(SearchEngineType supportedSearchEngineType : searchEngineTypes){
            this.supportedSearchEngineTypes.add(supportedSearchEngineType);
        }
    }

    @Override
    public SpellType getType() {
        return SpellType.SEARCH;
    }

    public List<SearchEngineType> getSupportedSearchEngines(){
        return this.supportedSearchEngineTypes;
    }
}
