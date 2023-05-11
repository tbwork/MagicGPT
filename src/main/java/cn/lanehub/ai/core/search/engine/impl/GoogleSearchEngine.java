package cn.lanehub.ai.core.search.engine.impl;

import cn.lanehub.ai.core.search.SearchEngineType;
import cn.lanehub.ai.core.search.SearchResult;

import java.util.Date;

public class GoogleSearchEngine extends AbstractSearchEngine{

    public static final GoogleSearchEngine INSTANCE = new GoogleSearchEngine();

    private GoogleSearchEngine() {
        super(SearchEngineType.GOOGLE);
    }

    @Override
    protected SearchResult doSearch(String keywords, Date startDate, Date endDate) {
        return null;
    }
}
