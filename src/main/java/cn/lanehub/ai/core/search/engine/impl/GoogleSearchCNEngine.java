package cn.lanehub.ai.core.search.engine.impl;

import cn.lanehub.ai.core.search.SearchEngineType;
import cn.lanehub.ai.core.search.SearchResult;
import cn.lanehub.ai.core.search.SearchResultItem;
import cn.lanehub.ai.network.impl.simulate.GetSimulateAccess;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoogleSearchCNEngine extends AbstractGoogleSearchEngine {

    public static final GoogleSearchCNEngine INSTANCE = new GoogleSearchCNEngine();

    private GoogleSearchCNEngine() {
        super(SearchEngineType.GOOGLE_CN);
    }

    @Override
    protected String getUrl(String keywords, Date startDate, Date endDate) {
        return this.getType().getUrl() + "search?ie=UTF-8&q=" + keywords;
    }
}
