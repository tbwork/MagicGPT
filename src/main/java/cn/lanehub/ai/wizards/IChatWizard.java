package cn.lanehub.ai.wizards;

import cn.lanehub.ai.brain.IThinkProcessor;
import cn.lanehub.ai.core.spell.book.IMagicSpellBook;
import cn.lanehub.ai.prompts.IPrompt;
import cn.lanehub.ai.prompts.Language;
import cn.lanehub.ai.wizards.model.CustomPrompt;
import cn.lanehub.ai.wizards.model.MagicChat;

import java.io.OutputStream;
import java.util.List;

public interface IChatWizard {

    /**
     * 根据已有的对话，生成AI回答，返回为一个流式对象。
     * @return
     */
    void generate(MagicChat magicChat, OutputStream outputStream);


    /**
     * 根据已有的对话，生成AI回答，返回为一个流式对象。
     * @return
     */
    void forceGenerate(MagicChat magicChat, OutputStream outputStream);


    IPrompt getSystemPrompt();


    void executeSpells(MagicChat magicChat, List<String> spellTexts);


    IMagicSpellBook getMagicBook();


    MagicChat startChat(CustomPrompt customPrompt, Language language);


    List<IThinkProcessor> getBrains();

}
