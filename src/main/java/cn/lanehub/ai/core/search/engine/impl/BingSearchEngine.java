package cn.lanehub.ai.core.search.engine.impl;

import cn.lanehub.ai.core.search.SearchEngineType;
import cn.lanehub.ai.core.search.SearchResult;

import java.util.Date;

public class BingSearchEngine extends AbstractSearchEngine{

    public static final BingSearchEngine INSTANCE = new BingSearchEngine();

    private BingSearchEngine() {
        super(SearchEngineType.BING);
    }

    @Override
    protected SearchResult doSearch(String keywords, Date startDate, Date endDate) {
        return null;
    }
}