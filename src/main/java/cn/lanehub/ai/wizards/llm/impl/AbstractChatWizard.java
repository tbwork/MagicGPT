package cn.lanehub.ai.wizards.llm.impl;

import cn.lanehub.ai.core.spell.book.impl.MagicSpellBook;
import cn.lanehub.ai.core.spell.manager.ISpellManager;
import cn.lanehub.ai.core.spell.book.IMagicSpellBook;
import cn.lanehub.ai.exceptions.AIBusyException;
import cn.lanehub.ai.model.Role;
import cn.lanehub.ai.model.WizardStatus;
import cn.lanehub.ai.prompts.IPrompt;
import cn.lanehub.ai.prompts.Language;
import cn.lanehub.ai.util.MessageUtil;
import cn.lanehub.ai.util.PromptUtil;
import cn.lanehub.ai.util.StringUtil;
import cn.lanehub.ai.wizards.IChatWizard;
import cn.lanehub.ai.wizards.model.MagicChat;
import cn.lanehub.ai.wizards.model.MagicMessage;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import cn.lanehub.ai.wizards.readers.AIResponseStreamReadTask;
import cn.lanehub.ai.wizards.readers.StreamReaderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractChatWizard implements IChatWizard {

    private static final Logger logger = LoggerFactory.getLogger(AbstractChatWizard.class);

    private IMagicSpellBook magicSpellBook ;


    protected final int maxMessageTokenCount;

    protected final int maxChatTokenCount;

    public AbstractChatWizard(String aiName, int maxMessageTokenCount, int maxChatTokenCount){
        this.magicSpellBook = new MagicSpellBook();
        this.maxMessageTokenCount = maxMessageTokenCount;
        this.maxChatTokenCount = maxChatTokenCount;
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

        String aiResponseText = sb.toString();
        logger.debug("{}:{}", "system", aiResponseText);

        // 按照最大长度拆分字符串为多个message
        String [] responseSegments = StringUtil.splitStringByLength(aiResponseText, this.maxMessageTokenCount);

        for(int i = 0; i< responseSegments.length; i++){
            String responseSegment = responseSegments[i];
            if(i == 0){
                responseSegment = "#S# " + responseSegment;
            }
            if(i == responseSegments.length -1){
                responseSegment += " #E#";
            }
            magicChat.appendMessage(new MagicMessage("system", responseSegment));
        }

        //  恢复进来时的状态。
        magicChat.setStatus(statusStore);

    }



    @Override
    public void forceGenerate(MagicChat magicChat, OutputStream outputStream) {

        // 如果Chat中存在
        if()

        // 对chat中tokens数量超量时进行优化，确保不超量即可
        MessageUtil.optimizeChat(magicChat, maxChatTokenCount);
        magicChat.setWizardStatus(WizardStatus.RESPONDING);
        // 执行AI，获得回答的输入流，开启读取线程，不断的读取、处理、并输出到指定的输出流上。
        InputStream inputStream = doGenerate(magicChat);
        AIResponseStreamReadTask AIResponseStreamReadTask =new AIResponseStreamReadTask(inputStream, outputStream, this, magicChat);
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
    public IMagicSpellBook getMagicBook(){
        return this.magicSpellBook;
    }




    protected abstract InputStream doGenerate(MagicChat magicChat);



    private boolean needAIGenerate(MagicChat magicChat){

        MagicMessage magicMessage = magicChat.getLatestMessage();

        if(Role.SYSTEM.getValue().equals(magicMessage.getRole())){
            //如果是System的结果
            return magicMessage.getContent().startsWith("");
        }

    }

}
