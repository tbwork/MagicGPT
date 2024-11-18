package com.magicvector.ai;

import com.magicvector.ai.brain.Brain;
import com.magicvector.ai.brain.llm.openai.OpenAIBrain;
import com.magicvector.ai.core.Spell;
import com.magicvector.ai.core.register.SpellLoader;
import com.magicvector.ai.prompts.Language;
import com.magicvector.ai.wizards.IChatWizard;
import com.magicvector.ai.wizards.impl.ChatWizard;
import com.magicvector.ai.wizards.model.CustomPrompt;
import com.magicvector.ai.wizards.model.MagicChat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.OutputStream;

public class MagicGPT {

    private static final Logger logger = LoggerFactory.getLogger(MagicGPT.class);

    private IChatWizard chatWizard;

    /**
     * 是否为保持模式，默认为false。
     * 保持模式下，所有消息会被缓存到上下文。
     * 非保持模式下，只有第一条SYSTEM提示词会保留，其余会被清空。
     */
    private boolean holdMode = false;

    /**
     * 创建一个MagicGPT帮助类，自动搜索并注册指定包名下的所有Call类型咒语
     * @param rootPackage 指定搜索的根包名
     * @param modelName 模型的名称
     * @param holdMode 是否保持对话
     */
    public MagicGPT(String rootPackage, String modelName, boolean holdMode){
        this(rootPackage, modelName,  50, holdMode);
    }

    /**
     * 创建一个MagicGPT帮助类，自动搜索并注册指定包名下的所有Call类型咒语
     * @param rootPackage 指定搜索的根包名
     * @param brain AI大脑
     */
    public MagicGPT(String rootPackage, Brain brain){
        this(rootPackage, brain, false);
    }

    /**
     * 创建一个MagicGPT帮助类，自动搜索并注册指定包名下的所有Call类型咒语
     * @param rootPackage 指定搜索的根包名
     * @param brain AI大脑
     * @param holdMode 是否保持对话
     */
    public MagicGPT(String rootPackage, Brain brain, boolean holdMode){
        this(rootPackage, brain, 50, holdMode);
    }

    /**
     * 创建一个MagicGPT帮助类，自动搜索并注册指定包名下的所有Call类型咒语
     * @param rootPackage 指定搜索的根包名
     * @param brain AI大脑
     * @param spells 显式申明的咒语对象列表
     */
    public MagicGPT(String rootPackage, Brain brain, Spell... spells){
        this(rootPackage, brain, 50, false, spells);
    }



    /**
     * 创建一个基于OpenAI的MagicGPT帮助类，自动搜索并注册指定包名下的所有Call类型咒语
     * @param rootPackage 指定搜索的根包名
     * @param modelName 模型名称
     * @param spells 显式申明的咒语对象列表
     */
    public MagicGPT(String rootPackage, String modelName, Spell... spells){
       this(rootPackage, modelName, 50, false, spells);
    }

    /**
     * 创建一个MagicGPT帮助类
     *
     * @param modelName 模型名称
     * @param holdMode 是否保持回话记录
     * @param spells 显式申明的咒语对象列表
     */
    public MagicGPT(String modelName, boolean holdMode, Spell ... spells){
        this("", modelName, 50, holdMode, spells);
    }


    /**
     * 创建一个MagicGPT帮助类，自动搜索并注册指定包名下的所有Call类型咒语
     * @param rootPackage 指定搜索的根包名
     * @param modelName AI模型名称
     * @param maxRounds 使用咒语的最大轮数（防止陷入无限调用）
     * @param spells 显式申明的咒语对象列表
     * @param holdMode 是否保持对话
     */
    public MagicGPT(String rootPackage, String modelName, int maxRounds, boolean holdMode, Spell... spells){
        this(rootPackage, new OpenAIBrain(modelName), maxRounds, holdMode, spells);
    }

    /**
     * 创建一个MagicGPT帮助类，自动搜索并注册指定包名下的所有Call类型咒语
     * @param rootPackage 指定搜索的根包名
     * @param brain AI魔法师的大脑
     * @param maxRounds 使用咒语的最大轮数（防止陷入无限调用）
     * @param spells 显式申明的咒语对象列表
     * @param holdMode 是否保持对话
     */
    public MagicGPT(String rootPackage, Brain brain, int maxRounds, boolean holdMode, Spell... spells){
        this.holdMode = holdMode;
        SpellLoader.loadAndRegister(rootPackage, spells);
        this.chatWizard = new ChatWizard(brain, maxRounds);
    }




    /**
     * 创建一个对话
     *
     * @param customPrompt 用户自定义的提示词
     * @param language 使用何种语言进行对话
     * @return
     */
    public MagicChat startChat(CustomPrompt customPrompt, Language language){
        return this.chatWizard.startChat(customPrompt, language);
    }


    public void proceedChat(MagicChat magicChat, OutputStream outputStream){
        if(!holdMode){
            magicChat.clearConversation();
        }
        chatWizard.doThink(magicChat, outputStream);

    }


    public void proceedChatWithUserMessage(MagicChat magicChat, String userMessageContent, OutputStream outputStream){
        if(!holdMode){
            magicChat.clearConversation();
        }
        magicChat.appendUserMessage(userMessageContent);
        chatWizard.doThink(magicChat, outputStream);
    }

}
