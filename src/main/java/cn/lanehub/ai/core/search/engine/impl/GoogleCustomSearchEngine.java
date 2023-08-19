package cn.lanehub.ai.core.search.engine.impl;

import cn.lanehub.ai.core.search.SearchEngineType;
import cn.lanehub.ai.core.search.SearchResult;
import cn.lanehub.ai.core.search.SearchResultItem;
import cn.lanehub.ai.core.search.model.GoogleCustomSearchInformation;
import cn.lanehub.ai.core.search.model.GoogleCustomSearchItem;
import cn.lanehub.ai.core.search.model.GoogleCustomSearchResult;
import cn.lanehub.ai.network.impl.restful.GetRestfulAccess;
import okhttp3.HttpUrl;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tbwork.anole.loader.Anole;
import org.tbwork.anole.loader.util.JSON;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shawn feng
 * @description
 * @date 2023/5/16 09:32
 */
public class GoogleCustomSearchEngine extends AbstractSearchEngine {

    public static final GoogleCustomSearchEngine INSTANCE = new GoogleCustomSearchEngine();
    private static final Logger logger = LoggerFactory.getLogger(GoogleCustomSearchEngine.class);

    private static final String GOOGLE_API_KEY = Anole.getProperty("magicgpt.config.spell.search.engine.google.api.key");
    private static final String GOOGLE_API_CX = Anole.getProperty("magicgpt.config.spell.search.engine.google.api.cx");

    private GoogleCustomSearchEngine() {
        super(SearchEngineType.GOOGLE_API);
    }

    @Override
    protected SearchResult doSearch(String keywords, Date startDate, Date endDate) {
        String url = HttpUrl.parse(this.getType().getUrl())
                .newBuilder()
                .addQueryParameter("key", GOOGLE_API_KEY)
                .addQueryParameter("cx", GOOGLE_API_CX)
                .addQueryParameter("q", keywords)
                .build().toString();

        String result = GetRestfulAccess.INSTANCE.access(url, null, null, null);
        SearchResult searchResult = new SearchResult();
        GoogleCustomSearchResult customSearchResult = JSON.parseObject(result, GoogleCustomSearchResult.class);

        if (customSearchResult == null || CollectionUtils.isEmpty(customSearchResult.getItems())) {
            logger.warn("invalid key or cx provided.please check out your key and cx");
            return searchResult;
        }

        List<GoogleCustomSearchItem> items = customSearchResult.getItems();
        GoogleCustomSearchInformation searchInformation = customSearchResult.getSearchInformation();
        searchResult.setCurrentPage(1);
        try {
            int total = Integer.parseInt(searchInformation.getTotalResults());
            searchResult.setTotalPageCount(total);
        } catch (Exception ignored) {
        }

        List<SearchResultItem> searchResultItems = items.stream().map(item -> {
            SearchResultItem searchResultItem = new SearchResultItem();
            searchResultItem.setUrl(item.getLink());
            searchResultItem.setTitle(item.getTitle());
            searchResultItem.setBrief(item.getSnippet());
            return searchResultItem;
        }).collect(Collectors.toList());
        searchResult.setSearchResult(searchResultItems);
        return searchResult;
    }
}
