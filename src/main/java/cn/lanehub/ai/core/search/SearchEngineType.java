package cn.lanehub.ai.core.search;

public enum SearchEngineType {
    BAIDU("baidu", "百度搜索，中国最大的搜索引擎，中文搜索优先使用该搜索引擎","https://www.baidu.com/"),
    GOOGLE("google", "谷歌搜索，全球最大的搜索引擎，涉及政治、伦理等敏感信息时采用该搜索引擎。","https://www.google.com/"),
    BING("bing", "必应搜索，微软提供全球型搜索引擎，支持中国地区访问，可以作为中文的补充搜索引擎。","https://www.bing.com/"),
    GOOGLE_CN("googleCN", "谷歌搜索，全球最大的搜索引擎，涉及政治、伦理等敏感信息时采用该搜索引擎。","https://www.google.com.hk/"),
    GOOGLE_API("googleAPI", "谷歌搜索API形式，全球最大的搜索引擎，涉及政治、伦理等敏感信息时采用该搜索引擎。","https://www.googleapis.com/customsearch/v1");

    private final String value;

    private final String description;
    private final String url;

    SearchEngineType(String value, String description, String url) {
        this.value = value;
        this.description = description;
        this.url = url;
    }

    public String getValue() {
        return value;
    }
    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public static SearchEngineType fromValue(String value) {
        for (SearchEngineType engineType : values()) {
            if (engineType.value.equals(value)) {
                return engineType;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }


}
