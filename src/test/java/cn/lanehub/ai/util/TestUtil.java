package cn.lanehub.ai.util;


import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

public class TestUtil {


    public static OutputStream getConsoleOutputStream(){
        return new SystemOutputStream();
    }


}
