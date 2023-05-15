package cn.lanehub.ai.wizards.impl;

import cn.lanehub.ai.brain.IThinkProcessor;
import cn.lanehub.ai.core.spell.book.impl.MagicSpellBook;
import cn.lanehub.ai.core.spell.manager.ISpellManager;
import cn.lanehub.ai.core.spell.book.IMagicSpellBook;
import cn.lanehub.ai.exceptions.AIBusyException;
import cn.lanehub.ai.model.WizardStatus;
import cn.lanehub.ai.prompts.IPrompt;
import cn.lanehub.ai.prompts.Language;
import cn.lanehub.ai.util.PromptUtil;
import cn.lanehub.ai.wizards.IChatWizard;
import cn.lanehub.ai.wizards.model.MagicChat;
import cn.lanehub.ai.wizards.model.MagicMessage;

import java.io.OutputStream;
import java.util.List;

import cn.lanehub.ai.wizards.readers.AIResponseStreamReadTask;
import cn.lanehub.ai.wizards.readers.StreamReaderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatWizard implements IChatWizard {

    private static final Logger logger = LoggerFactory.getLogger(ChatWizard.class);

    private IMagicSpellBook magicSpellBook ;

    private List<IThinkProcessor> brain;


    public ChatWizard(List<IThinkProcessor> brain){
        this.magicSpellBook = new MagicSpellBook();
        this.brain = brain;
    }

    @Override
    public MagicChat startChat(String customPrompt, Language language){
        // 生成system角色提示词。
        IPrompt firstSystemPrompt = this.getSystemPrompt();
        String systemPrompt =  customPrompt + "\n" + firstSystemPrompt.getPrompt() + "\n" + PromptUtil.formatPrompt("请使用{}回答问题", language.getChinese()) ;
        logger.debug("The first system prompt is: \n{}", systemPrompt);
        // 传入system提示词
        MagicChat magicChat = new MagicChat(language);
        magicChat.appendSystemMessage(systemPrompt);
        return magicChat;
    }

    @Override
    public List<IThinkProcessor> getBrain() {
        return this.brain;
    }


    @Override
    public void executeSpells(MagicChat magicChat, List<String> spellTexts){

        WizardStatus statusStore = magicChat.getWizardStatus();
        // 进入念咒语的阶段
        magicChat.setStatus(WizardStatus.SPELLING);

        if(spellTexts.isEmpty()){
            return;
        }

        int p =1;
        StringBuilder sb = new StringBuilder();
        for(String spellText:spellTexts){
            sb.append("["+ p++ +"] ");
            ISpellManager spellManager =  this.magicSpellBook.resolve(spellText);
            logger.debug("AI 咒语：{}", spellText);
            String spellResult = spellManager.castSpell(spellText);
            sb.append(spellResult).append("\n");
        }

        String spellResultText =  "#S# "  +  sb.toString() +  " #E#";
        logger.debug("{}:{}", "Spell executed successfully, system", spellResultText);

        magicChat.appendMessage(new MagicMessage("system", spellResultText));

        //  恢复进来时的状态。
        magicChat.setStatus(statusStore);

    }



    @Override
    public void forceGenerate(MagicChat magicChat, OutputStream outputStream) {
        AIResponseStreamReadTask AIResponseStreamReadTask =new AIResponseStreamReadTask(this.brain, outputStream, magicChat);
        StreamReaderManager.INSTANCE.submitTask(AIResponseStreamReadTask);
    }


    @Override
    public void generate(MagicChat magicChat, OutputStream outputStream) {

        // 如果AI在忙，报错
        if(!magicChat.isIdle()){
            throw new AIBusyException(magicChat.getWizardStatus());
        }

       this.forceGenerate(magicChat, outputStream);

    }


    @Override
    public IPrompt getSystemPrompt() {
        return getMagicBook().getFirstSystemPrompt();
    }


    @Override
    public IMagicSpellBook getMagicBook(){
        return this.magicSpellBook;
    }


}
