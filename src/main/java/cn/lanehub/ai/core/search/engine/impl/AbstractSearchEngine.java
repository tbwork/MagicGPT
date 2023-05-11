package cn.lanehub.ai.core.search.engine.impl;

import cn.lanehub.ai.core.search.SearchEngineType;
import cn.lanehub.ai.core.search.engine.ISearchEngine;
import cn.lanehub.ai.core.search.engine.SearchEngineRepository;
import cn.lanehub.ai.core.search.SearchResult;
import cn.lanehub.ai.exceptions.Assert;
import org.tbwork.anole.loader.util.JSON;

import java.util.Date;

public abstract  class AbstractSearchEngine implements ISearchEngine {

    private SearchEngineType searchEngineType;

    protected AbstractSearchEngine(SearchEngineType searchEngineType){
        this.searchEngineType = searchEngineType;
        SearchEngineRepository.INSTANCE.registerEngine(this.searchEngineType, this);
    }


    @Override
    public String search(String keywords, Date startDate, Date endDate) {

        Assert.isNotBlank(keywords, "keywords");
        Object searchResult = doSearch(keywords,  startDate, endDate);

        return JSON.toJSONString(searchResult);
    }

    @Override
    public SearchEngineType getType() {
        return this.searchEngineType;
    }

    protected abstract SearchResult doSearch(String keywords, Date startDate, Date endDate);
}
