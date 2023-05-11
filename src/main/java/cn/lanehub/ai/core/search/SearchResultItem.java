package cn.lanehub.ai.core.search;

import lombok.Data;

import java.io.Serializable;

@Data
public class SearchResultItem implements Serializable {

    private String title;
    private String brief;
    private String url;

}
