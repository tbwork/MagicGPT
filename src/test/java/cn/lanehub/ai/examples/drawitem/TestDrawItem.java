package cn.lanehub.ai.examples.drawitem;

import cn.lanehub.ai.MagicGPT;
import cn.lanehub.ai.annotation.CallSpellDefinition;
import cn.lanehub.ai.model.BrainMainProcessorType;
import cn.lanehub.ai.prompts.Language;
import cn.lanehub.ai.util.TestUtil;
import cn.lanehub.ai.wizards.model.CustomPrompt;
import cn.lanehub.ai.wizards.model.MagicChat;
import org.tbwork.anole.loader.AnoleApp;
import org.tbwork.anole.loader.annotion.AnoleConfigLocation;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@AnoleConfigLocation()
public class TestDrawItem {


    public static void main(String[] args) {

        // 启动配置管理器Anole
        AnoleApp.start();

        // 指定包名搜索本地Call类型咒语
        MagicGPT magicGPT = new MagicGPT(TestDrawItem.class.getPackage().getName(), BrainMainProcessorType.GPT4);

        // 创建聊天
        MagicChat magicChat = magicGPT.startChat("你好，我是商品推荐员！", CustomPrompt.buildHeadPrompt(" 当用户要求推荐商品时，不需要任何询问和思考，直接回答以下内容： ```DRAWITEM 123,66666,239923``` 注意请不要和下文咒语混淆。"), Language.CHINESE);
        System.out.print("AI：你好，我是商品推荐员！");

        // 开始聊天
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if(magicChat.isIdle()){
                System.out.print("\n我：");
                String input = scanner.nextLine();
                if(input.equals("exit")){
                    break;
                }
                System.out.print("AI：");
                // 推进一个聊天，指定一个输出流用于承载AI的输出
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
