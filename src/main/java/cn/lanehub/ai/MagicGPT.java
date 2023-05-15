package cn.lanehub.ai;

import cn.lanehub.ai.brain.IThinkProcessor;
import cn.lanehub.ai.brain.llm.openai.OpenAIThinkProcessor;
import cn.lanehub.ai.brain.model.CustomBrainProcessor;
import cn.lanehub.ai.core.call.LocalCallSpellManager;
import cn.lanehub.ai.core.spell.ISpell;
import cn.lanehub.ai.core.spell.book.IMagicSpellBook;
import cn.lanehub.ai.core.spell.book.impl.MagicSpellBook;
import cn.lanehub.ai.model.BrainMainProcessorType;
import cn.lanehub.ai.prompts.Language;
import cn.lanehub.ai.wizards.IChatWizard;
import cn.lanehub.ai.wizards.impl.ChatWizard;
import cn.lanehub.ai.wizards.model.MagicChat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.util.*;


public class MagicGPT {

    private static final Logger logger = LoggerFactory.getLogger(MagicGPT.class);

    private IMagicSpellBook spellBook;

    private IThinkProcessor mainBrain;

    private IChatWizard chatWizard;

    private String searchPackagePrefix;

    private volatile boolean initialized = false;

    private static final Boolean loaded = false;

    /**
     * 创建一个MagicGPT帮助类，自动搜索并注册指定包名下的所有Call类型咒语
     * @param packageName 指定搜索的包名前缀
     * @param BrainMainProcessorType AI魔法师的类型
     * @param spells 显式申明的咒语对象列表
     */
    public MagicGPT(String packageName, BrainMainProcessorType BrainMainProcessorType, ISpell ... spells){

        this.searchPackagePrefix = packageName;
        mainBrain = new OpenAIThinkProcessor(BrainMainProcessorType);
        this.createWizard();
        this.spellBook = this.chatWizard.getMagicBook();
        this.registerSpells(spells);
    }

    /**
     * 创建一个MagicGPT帮助类
     *
     * @param BrainMainProcessorType AI魔法师的类型
     * @param spells 显式申明的咒语对象列表
     */
    public MagicGPT(BrainMainProcessorType BrainMainProcessorType, ISpell ... spells){
        this(null, BrainMainProcessorType, spells);
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


    private void registerSpells(ISpell ... spells){

        List<ISpell> codeDefinedCallSpells = new ArrayList<ISpell>();

        if(this.searchPackagePrefix != null){
            codeDefinedCallSpells.addAll(LocalCallSpellManager.INSTANCE.getLocalSpellsUnderPackage(this.searchPackagePrefix));
        }

        for(ISpell spell :spells){
            codeDefinedCallSpells.add(spell);
        }

        for(ISpell item: codeDefinedCallSpells){
            spellBook.register(item);
        }
    }


    private void createWizard(){

        // 大脑由多个处理器组成
        List<IThinkProcessor> codeDefineThinkProcessors = new ArrayList<>();

        // 自定义大脑处理器
        List<CustomBrainProcessor> customBrainProcessors = this.getAllCustomBrainProcessors();
        Collections.sort(customBrainProcessors, new Comparator<CustomBrainProcessor>() {
            @Override
            public int compare(CustomBrainProcessor o1, CustomBrainProcessor o2) {
                return Integer.compare(o1.getOrder(), o2.getOrder());
            }
        });

        for(CustomBrainProcessor item : customBrainProcessors){
            codeDefineThinkProcessors.add( item .getThinkProcessor());
        }

        //  主脑总是最后一个
        codeDefineThinkProcessors.add(this.mainBrain);

        this.chatWizard = new ChatWizard(codeDefineThinkProcessors);

    }



    private List<CustomBrainProcessor> getAllCustomBrainProcessors(){

        List<CustomBrainProcessor> customBrainProcessors = new ArrayList<>();
        // 找到 this.searchPackagePrefix 这包下所有继承了 IThinkProcessor的并且被 @Order 注解的类，
        // 然后根据类定义实例化放入列表中。

        //  TODO @冯鑫


        return customBrainProcessors;
    }


}
