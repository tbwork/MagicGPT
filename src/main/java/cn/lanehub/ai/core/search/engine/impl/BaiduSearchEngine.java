package cn.lanehub.ai.core.search.engine.impl;

import cn.lanehub.ai.core.search.SearchEngineType;
import cn.lanehub.ai.core.search.SearchResult;
import cn.lanehub.ai.core.search.SearchResultItem;
import cn.lanehub.ai.network.impl.simulate.GetSimulateAccess;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaiduSearchEngine extends AbstractSearchEngine{

    public static final BaiduSearchEngine INSTANCE = new BaiduSearchEngine();

    private BaiduSearchEngine(){
        super(SearchEngineType.BAIDU);
    }

    @Override
    protected SearchResult doSearch(String keywords, Date startDate, Date endDate) {

        // 构造检索URL地址
        String url = this.getType().getUrl() + "/s?ie=UTF-8&wd=" + keywords;

        String access = GetSimulateAccess.INSTANCE.access(url, null, null, null);
        return getSearchResult(access);
    }


    /**
     * 解析百度返回搜索列表
     * @param pageSource
     * @return
     */
    private SearchResult getSearchResult(String pageSource) {
        SearchResult searchResult = new SearchResult();
        searchResult.setCurrentPage(1);
        try {
            Document parse = Jsoup.parse(pageSource);
            Element content_left = parse.getElementById("content_left");
            List<Element> childElementsList = content_left.children();
            if (childElementsList != null) {
                List<SearchResultItem> searchResultItems = new ArrayList<>();
                for (Element element : childElementsList) {
                    SearchResultItem searchResultItem = new SearchResultItem();
                    Elements elementsByClass = element.getElementsByClass("t");
                    if (elementsByClass != null && elementsByClass.size() > 0) {
                        Element titleElement = elementsByClass.get(0);
                        searchResultItem.setTitle(titleElement.text());
                        if (titleElement.getElementsByTag("a") != null && titleElement.getElementsByTag("a").size() > 0) {
                            String attr = titleElement.getElementsByTag("a").get(0).attr("href");
                            //过滤掉百度广告
                            if (attr != null && attr.startsWith("http://www.baidu.com/baidu.php")) {
                                continue;
                            }
                            searchResultItem.setUrl(attr);
                        }

                    }
                    if (element.getElementsByClass("c-font-normal") != null && element.getElementsByClass("c-font-normal").size() > 0) {

                        String text = element.getElementsByClass("c-font-normal").text();
                        searchResultItem.setBrief(text);
                    } else if (searchResultItem.getBrief() == null
                            && element.getElementsByClass("content-right_8Zs40") != null
                            && element.getElementsByClass("content-right_8Zs40").size() > 0) {
                        String text = element.getElementsByClass("content-right_8Zs40").text();
                        searchResultItem.setBrief(text);
                    }
                    if (searchResultItem.getTitle() != null) {
                        searchResultItems.add(searchResultItem);
                    }
                }
                searchResult.setSearchResult(searchResultItems);
            }
            Elements elementsByClass = parse.getElementsByClass("page-item_M4MDr pc");
            if (elementsByClass != null && elementsByClass.size() > 0) {
                Element element = elementsByClass.get(elementsByClass.size() - 1);
                searchResult.setTotalPageCount(Integer.parseInt(element.text()));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return searchResult;
    }


}
