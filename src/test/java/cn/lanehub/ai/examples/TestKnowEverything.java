package cn.lanehub.ai.examples;

import cn.lanehub.ai.MagicGPT;
import cn.lanehub.ai.core.search.SearchEngineType;
import cn.lanehub.ai.core.search.SearchSpell;
import cn.lanehub.ai.core.spell.ISpell;
import cn.lanehub.ai.core.view.ViewSpell;
import cn.lanehub.ai.model.AIWizardType;
import cn.lanehub.ai.prompts.Language;
import cn.lanehub.ai.util.PromptUtil;
import cn.lanehub.ai.util.TestUtil;
import cn.lanehub.ai.wizards.model.MagicChat;
import org.tbwork.anole.loader.AnoleApp;
import org.tbwork.anole.loader.annotion.AnoleConfigLocation;
import org.tbwork.anole.loader.util.AnoleLogger;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@AnoleConfigLocation()
public class TestKnowEverything {



    public static void main(String[] args) {


        AnoleApp.start(AnoleLogger.LogLevel.INFO);
        String customSystemPrompt = PromptUtil.readTestResourceFile("custom_prompts/knowledge_master.prompt");


        ISpell searchSpell = new SearchSpell(new SearchEngineType[]{SearchEngineType.BAIDU});
        ISpell viewSpell = new ViewSpell();

        ISpell [] spells = new ISpell[]{searchSpell, viewSpell};


        MagicGPT magicGPT = new MagicGPT("张半仙",  AIWizardType.GPT4,  spells);

        // 进行聊天
        MagicChat magicChat = magicGPT.startChat("你好，我是张半仙，有啥事尽管说来？",customSystemPrompt, Language.CHINESE);
        System.out.print("AI：你好，我是张半仙，有啥事尽管说来!");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            if(magicChat.isIdle()){
                System.out.print("\n我：");
                String input = scanner.nextLine();
                if(input.equals("exit")){
                    break;
                }
                System.out.print("AI：");
                magicGPT.proceedChatWithUserMessage(input, magicChat, TestUtil.getConsoleOutputStream());
            }

            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
