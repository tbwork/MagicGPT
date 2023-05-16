package cn.lanehub.ai.core.search.engine.impl;

import cn.lanehub.ai.core.search.SearchEngineType;
import cn.lanehub.ai.core.search.SearchResult;
import cn.lanehub.ai.core.search.SearchResultItem;
import cn.lanehub.ai.network.impl.simulate.GetSimulateAccess;
import com.google.common.collect.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * @author shawn feng
 * @description
 * @date 2023/5/16 15:28
 */
public abstract class AbstractGoogleSearchEngine extends AbstractSearchEngine {

    private static final Logger logger = LoggerFactory.getLogger(AbstractGoogleSearchEngine.class);

    protected AbstractGoogleSearchEngine(SearchEngineType searchEngineType) {
        super(searchEngineType);
    }

    @Override
    protected SearchResult doSearch(String keywords, Date startDate, Date endDate) {
        String url = getUrl(keywords, startDate, endDate);
        String page = GetSimulateAccess.INSTANCE.access(url, null, null, null);
        return analysisPage(page);
    }

    protected abstract String getUrl(String keywords, Date startDate, Date endDate);

    private final SearchResult analysisPage(String page) {
        SearchResult searchResult = new SearchResult();
        searchResult.setCurrentPage(1);
        List<SearchResultItem> list = Lists.newArrayList();

        try {
            Document document = Jsoup.parse(page);
            //body -> main -> GyAeWb(rcnt) -> res -> search -> rso -> MjjYud(g)

            Element resultStat = document.getElementById("result-stats");
            if (resultStat != null) {
                String text = resultStat.ownText();
                String numberString = text.replaceAll("[^0-9]", ""); // 去除非数字字符
                numberString = numberString.replace(",", ""); // 去除逗号

                long number = Long.parseLong(numberString);
                searchResult.setTotalPageCount((int) (number / 10));
            }

            Elements searchElements = document.body().getElementById("search").getElementById("rso").getElementsByClass("MjjYud");

            searchElements.forEach(element -> {
                SearchResultItem searchResultItem = new SearchResultItem();
                Elements websiteList = element.getElementsByClass("g");

                if (!websiteList.isEmpty()) {
                    Element websiteElement = websiteList.get(0);
                    Element yuRUbf = websiteElement.getElementsByClass("yuRUbf").get(0);

                    Element link = yuRUbf.getElementsByTag("a").get(0);
                    searchResultItem.setUrl(link.attr("href"));
                    searchResultItem.setTitle(link.getElementsByTag("h3").get(0).text());

                    Elements briefInfoList = websiteElement.getElementsByClass("IsZvec");
                    if (briefInfoList.isEmpty()) {
                        briefInfoList = websiteElement.getElementsByAttributeValue("data-sncf", "1");
                    }

                    if (!briefInfoList.isEmpty()) {
                        Element briefInfo = briefInfoList.get(0);
                        Elements span = briefInfo.getElementsByTag("span");
                        if (span.isEmpty()) {
                            span = briefInfo.getElementsByTag("div");
                        }
                        searchResultItem.setBrief(span.last().text());
                    }
                    list.add(searchResultItem);
                }
            });

        } catch (Exception exception) {
            logger.error("解析谷歌搜索结果失败", exception);
        }
        searchResult.setSearchResult(list);
        return searchResult;
    }
}
