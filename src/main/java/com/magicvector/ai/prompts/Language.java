package com.magicvector.ai.prompts;

public enum Language {
    ENGLISH("English", "英语"),
    CHINESE("Chinese", "中文");

    private final String englishName;
    private final String chinese;

    private Language(String englishName, String chinese) {
        this.englishName = englishName;
        this.chinese = chinese;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getChinese() {
        return chinese;
    }

    public static Language getLanguageByEnglishName(String englishName) {
        for (Language language : Language.values()) {
            if (language.getEnglishName().equals(englishName)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Invalid language name: " + englishName);
    }
}
