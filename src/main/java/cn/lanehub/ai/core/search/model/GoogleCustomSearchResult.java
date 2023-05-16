package cn.lanehub.ai.core.search.model;

import lombok.Data;

import java.util.List;

/**
 * @author shawn feng
 * @description
 * @date 2023/5/16 09:43
 */
@Data
public class GoogleCustomSearchResult {

    private String kind;
    private List<GoogleCustomSearchItem> items;
    private GoogleCustomSearchInformation searchInformation;
}
