package cn.lanehub.ai.util;


import cn.lanehub.ai.output.SystemOutputStream;

import java.io.OutputStream;

public class TestUtil {


    public static OutputStream getConsoleOutputStream(){
        return new SystemOutputStream();
    }


}
