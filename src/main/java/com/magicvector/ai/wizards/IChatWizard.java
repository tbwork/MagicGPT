package com.magicvector.ai.wizards;

import com.magicvector.ai.brain.Brain;
import com.magicvector.ai.prompts.IPrompt;
import com.magicvector.ai.prompts.Language;
import com.magicvector.ai.wizards.model.CustomPrompt;
import com.magicvector.ai.wizards.model.MagicChat;
import java.io.OutputStream;
import java.util.List;

public interface IChatWizard {

    /**
     * 根据已有的对话，生成AI回答，返回到一个输出流中。
     */
    void doThink(MagicChat magicChat, OutputStream outputStream);



    IPrompt getSystemPrompt();


    void executeSpells(MagicChat magicChat, List<String> spellTexts);


    MagicChat startChat(CustomPrompt customPrompt, Language language);


}
