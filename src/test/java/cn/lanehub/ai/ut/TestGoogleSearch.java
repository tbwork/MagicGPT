package cn.lanehub.ai.ut;

import cn.lanehub.ai.core.search.engine.impl.GoogleCustomSearchEngine;
import cn.lanehub.ai.core.search.engine.impl.GoogleSearchCNEngine;
import cn.lanehub.ai.core.search.engine.impl.GoogleSearchEngine;
import org.tbwork.anole.loader.AnoleApp;
import org.tbwork.anole.loader.annotion.AnoleConfigLocation;
import org.tbwork.anole.loader.util.AnoleLogger;

/**
 * @author shawn feng
 * @description
 * @date 2023/5/16 10:07
 */
@AnoleConfigLocation()
public class TestGoogleSearch {

    public static void main(String[] args) {
        AnoleApp.start(AnoleLogger.LogLevel.INFO);
        String java = GoogleCustomSearchEngine.INSTANCE.search("java", null, null);
        System.out.println(java);
        String goo = GoogleSearchEngine.INSTANCE.search("goo", null, null);
        System.out.println(goo);
        String hello = GoogleSearchCNEngine.INSTANCE.search("你好", null, null);
        System.out.println(hello);
    }
}
