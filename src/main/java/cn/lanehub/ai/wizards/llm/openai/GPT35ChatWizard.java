package cn.lanehub.ai.wizards.llm.openai;

import org.tbwork.anole.loader.Anole;

public class GPT35ChatWizard extends AbstractOpenAIChatWizard{

    public GPT35ChatWizard(String aiName, String modelName) {
        super(aiName,
                Anole.getIntProperty("magicgpt.config.chat.message.tokens.max.gpt3-5", 1000),
                Anole.getIntProperty("magicgpt.config.chat.tokens.max.gpt3-5", 3800),
                modelName
        );
    }
}