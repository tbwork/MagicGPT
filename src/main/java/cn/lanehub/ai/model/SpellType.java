package cn.lanehub.ai.model;

public enum SpellType {
    CALL("call", "调用API或者本地方法"),
    SEARCH("search", "通过搜索引擎检索网络"),
    SQL("sql", "指定数据库进行SQL查询"),
    VIEW("view", "浏览指定URL的网页"),
    MATCH("match", "在指定的向量库中匹配到指定的向量");

    private final String name;
    private final String description;

    SpellType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static SpellType fromName(String name) {
        for (SpellType spellType : SpellType.values()) {
            if (spellType.getName().equals(name)) {
                return spellType;
            }
        }
        throw new IllegalArgumentException("No enum constant " + SpellType.class.getName() + "." + name);
    }
}
