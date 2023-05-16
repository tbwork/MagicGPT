package cn.lanehub.ai;

import cn.lanehub.ai.core.search.engine.impl.GoogleCustomSearchEngine;
import org.tbwork.anole.loader.AnoleApp;
import org.tbwork.anole.loader.annotion.AnoleConfigLocation;
import org.tbwork.anole.loader.util.AnoleLogger;

/**
 * @author shawn feng
 * @description
 * @date 2023/5/16 10:07
 */
@AnoleConfigLocation()
public class TestGoogleCustomSearch {

    public static void main(String[] args) {
        AnoleApp.start(AnoleLogger.LogLevel.INFO);
        String java = GoogleCustomSearchEngine.INSTANCE.search("java", null, null);
        System.out.println(java);
    }
}
