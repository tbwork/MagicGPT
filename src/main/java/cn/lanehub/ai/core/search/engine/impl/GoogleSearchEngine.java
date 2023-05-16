package cn.lanehub.ai.core.search.engine.impl;

import cn.lanehub.ai.core.search.SearchEngineType;
import cn.lanehub.ai.core.search.SearchResult;
import cn.lanehub.ai.network.impl.simulate.GetSimulateAccess;

import java.util.Date;

public class GoogleSearchEngine extends AbstractGoogleSearchEngine {

    public static final GoogleSearchEngine INSTANCE = new GoogleSearchEngine();

    private GoogleSearchEngine() {
        super(SearchEngineType.GOOGLE);
    }

}
