package cn.lanehub.ai.core.search.engine;

import cn.lanehub.ai.core.search.SearchEngineType;

import java.util.Date;

/**
 *
 */
public interface ISearchEngine {

    String search(String keywords, Date startDate, Date endDate);


    SearchEngineType getType();
}
