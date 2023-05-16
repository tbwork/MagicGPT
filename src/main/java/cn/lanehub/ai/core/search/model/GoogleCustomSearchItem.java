package cn.lanehub.ai.core.search.model;

import lombok.Data;

/**
 * @author shawn feng
 * @description
 * @date 2023/5/16 09:44
 */
@Data
public class GoogleCustomSearchItem {

    private String kind;
    //搜索结果的标题（纯文本格式）
    private String title;
    //搜索结果的标题（HTML 格式）
    private String htmlTitle;
    //搜索结果指向的完整网址，例如 http://www.example.com/foo/bar
    private String link;
    //此搜索结果网址的缩略版本，例如 www.example.com
    private String displayLink;
    //搜索结果的摘要（纯文本格式）
    private String snippet;
    //搜索结果的摘要（HTML 格式）
    private String htmlSnippet;
    //指明 Google 缓存版本的搜索结果的 ID
    private String cacheId;
    //显示在每个搜索结果的摘要之后的网址
    private String formattedUrl;
    //在每个搜索结果的摘要之后显示的 HTML 格式的网址
    private String htmlFormattedUrl;
}
