package cn.lanehub.ai.core.search;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SearchResult implements Serializable {

    private List<SearchResultItem> searchResult;

    private int totalPageCount;

    private int currentPage;
}
