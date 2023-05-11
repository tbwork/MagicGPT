package cn.lanehub.ai;

import cn.lanehub.ai.core.call.LocalCallSpellManager;
import cn.lanehub.ai.core.spell.ISpell;
import cn.lanehub.ai.core.spell.book.IMagicSpellBook;
import cn.lanehub.ai.model.AIWizardType;
import cn.lanehub.ai.prompts.Language;
import cn.lanehub.ai.wizards.AIWizardFactory;
import cn.lanehub.ai.wizards.IChatWizard;
import cn.lanehub.ai.wizards.model.MagicChat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class MagicGPT {

    private static final Logger logger = LoggerFactory.getLogger(MagicGPT.class);

    private IMagicSpellBook spellBook;

    private IChatWizard chatWizard;

    private volatile boolean initialized = false;

    private static final Boolean loaded = false;

    /**
     * 创建一个MagicGPT帮助类，自动搜索并注册指定包名下的所有Call类型咒语
     * @param packageName 指定搜索的包名前缀
     * @param aiName AI的名称
     * @param aiWizardType AI魔法师的类型
     * @param spells 显式申明的咒语对象列表
     */
    public MagicGPT(String packageName,String aiName, AIWizardType aiWizardType, ISpell ... spells){
        chatWizard = AIWizardFactory.createAIWizard(aiName, aiWizardType);
        this.spellBook = chatWizard.getMagicBook();
        this.register(packageName, spells);
    }

    /**
     * 创建一个MagicGPT帮助类
     *
     * @param aiName AI的名称
     * @param aiWizardType AI魔法师的类型
     * @param spells 显式申明的咒语对象列表
     */
    public MagicGPT(String aiName, AIWizardType aiWizardType, ISpell ... spells){
        this(null, aiName, aiWizardType, spells);
    }


    /**
     * 创建一个对话
     *
     * @param greeting 第一个来自AI的问候语
     * @param customPrompt 用户自定义提示词
     * @param language 使用何种语言进行对话
     * @return
     */
    public MagicChat startChat(String greeting, String customPrompt, Language language){
        MagicChat magicChat = this.chatWizard.startChat(customPrompt, language);
        magicChat.appendAssistantMessage(greeting);
        return magicChat;
    }


    public void proceedChat(MagicChat magicChat, OutputStream outputStream){
        chatWizard.generate(magicChat, outputStream);
    }


    public void proceedChatWithUserMessage(String userMessageContent, MagicChat magicChat, OutputStream outputStream){
        magicChat.appendUserMessage(userMessageContent);
        this.proceedChat(magicChat, outputStream);
    }


    private void register(String packageNamePrefix, ISpell ... spells){
        List<ISpell> codeDefinedCallSpells = new ArrayList<ISpell>();

        if(packageNamePrefix != null){
            codeDefinedCallSpells.addAll(LocalCallSpellManager.INSTANCE.getLocalSpellsUnderPackage(packageNamePrefix));
        }

        for(ISpell spell :spells){
            codeDefinedCallSpells.add(spell);
        }

        for(ISpell item: codeDefinedCallSpells){
            spellBook.register(item);
        }
    }


}
