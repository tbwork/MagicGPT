package cn.lanehub.ai.wizards.readers;
import org.tbwork.anole.loader.Anole;

import java.util.concurrent.*;

public class StreamReaderManager {

    public static final StreamReaderManager INSTANCE = new StreamReaderManager();

    private int initialThreads;

    private int maxThreads;

    private int keepAliveTime;

    private ThreadPoolExecutor threadPool;

    private StreamReaderManager(){
        initialThreads = Anole.getIntProperty("magicgpt.config.reader.threads.initialCount", 100);
        maxThreads = Anole.getIntProperty("magicgpt.config.reader.threads.maxThreads", 1000);
        keepAliveTime = Anole.getIntProperty("magicgpt.config.reader.threads.keepAliveTime.second", 5);
        this.threadPool = new ThreadPoolExecutor(initialThreads, maxThreads, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    public void submitTask(AIResponseStreamReadTask streamReader) {
        threadPool.submit(streamReader);
    }

    public void shutdown() {
        threadPool.shutdown();
    }
}
