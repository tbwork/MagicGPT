package cn.lanehub.ai;

import cn.lanehub.ai.util.TestUtil;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class TestSysoutStream {


    public static void main(String[] args) throws IOException, InterruptedException {
        // 创建一个 PipedOutputStream 对象
        OutputStream outputStream = TestUtil.getConsoleOutputStream();

        outputStream.write("ABC".getBytes());

        TimeUnit.SECONDS.sleep(2);


        outputStream.write("ABC".getBytes());

        TimeUnit.SECONDS.sleep(2);

        outputStream.write("ABC".getBytes());


    }

}
