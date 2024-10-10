package com.magicvector.ai.util;

/**
 * @author shawn feng
 * @description
 * @date 2023/5/4 10:47
 */
public class JdkUtil {

    public static boolean isJava8OrAbove() {
        String version = System.getProperty("java.version");
        String[] parts = version.split("\\.");
        int major = Integer.parseInt(parts[0]);
        int minor = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
        return major >= 8 || (major == 1 && minor >= 8);
    }
}
