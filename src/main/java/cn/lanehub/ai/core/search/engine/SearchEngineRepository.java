package cn.lanehub.ai.core.search.engine;

import cn.lanehub.ai.core.search.SearchEngineType;

import java.util.HashMap;
import java.util.Map;

public class SearchEngineRepository {


    private static final Map<SearchEngineType, ISearchEngine> engineMap = new HashMap<SearchEngineType, ISearchEngine>();

    public static final SearchEngineRepository INSTANCE = new SearchEngineRepository();

    private SearchEngineRepository(){}


    public void registerEngine(SearchEngineType searchEngineType, ISearchEngine searchEngine){
        engineMap.put(searchEngineType, searchEngine);
    }

    public ISearchEngine getEngine(SearchEngineType searchEngineType){
        return engineMap.get(searchEngineType);
    }


}
