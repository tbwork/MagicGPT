package cn.lanehub.ai.core.search.model;

import lombok.Data;

/**
 * @author shawn feng
 * @description
 * @date 2023/5/16 09:50
 */
@Data
public class GoogleCustomSearchInformation {

    private Double searchTime;
    private String formattedSearchTime;
    private String totalResults;
    private String formattedTotalResults;
}
