package cn.lanehub.ai.wizards.llm.openai;

import org.tbwork.anole.loader.Anole;

public class GPT4ChatWizard extends AbstractOpenAIChatWizard{

    public GPT4ChatWizard(String aiName, String modelName) {
        super(aiName,
            Anole.getIntProperty("magicgpt.config.chat.message.tokens.max.gpt4", 3000),
            Anole.getIntProperty("magicgpt.config.chat.tokens.max.gpt4", 8000),
            modelName
        );
    }
}