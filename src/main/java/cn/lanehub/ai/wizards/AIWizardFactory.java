package cn.lanehub.ai.wizards;

import cn.lanehub.ai.exceptions.NotSupportedException;
import cn.lanehub.ai.model.AIWizardType;
import cn.lanehub.ai.wizards.llm.openai.GPT35ChatWizard;
import cn.lanehub.ai.wizards.llm.openai.GPT4ChatWizard;

public class AIWizardFactory {
    public static IChatWizard createAIWizard(String aiName, AIWizardType wizardType) {
        switch (wizardType) {
            case GPT3_5_TURBO:
                return new GPT35ChatWizard(aiName, wizardType.getValue());
            case GPT3_5_TURBO_0301:
                return new GPT35ChatWizard(aiName, wizardType.getValue());
            case GPT3_5_TEXT_DAVINCI_003:
                return new GPT35ChatWizard(aiName, wizardType.getValue());
            case GPT3_5_TEXT_DAVINCI_002:
                return new GPT35ChatWizard(aiName, wizardType.getValue());
            case GPT3_5_CODE_DAVINCI_002:
                return new GPT35ChatWizard(aiName, wizardType.getValue());
            case GPT4:
                return new GPT4ChatWizard(aiName, wizardType.getValue());
            case GPT4_0314:
                return new GPT4ChatWizard(aiName, wizardType.getValue());
            case GPT4_32K:
                return new GPT4ChatWizard(aiName, wizardType.getValue());
            case GPT4_32K_0314:
                return new GPT4ChatWizard(aiName, wizardType.getValue());
            case Alpaca:
                throw new NotSupportedException("alpaca of llm");
            default:
                throw new IllegalArgumentException("Unknown wizard type: " + wizardType);
        }
    }
}
