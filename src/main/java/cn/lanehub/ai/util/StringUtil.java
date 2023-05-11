package cn.lanehub.ai.util;

import java.util.regex.Pattern;

/**
 * @author shawn feng
 * @description
 * @date 2023/5/5 09:41
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }



    public static String[] splitStringByLength(String inputString, int length) {
        int strLength = inputString.length();
        int arrayLength = (int) Math.ceil((double) strLength / length);
        String[] result = new String[arrayLength];
        int j = 0;

        for (int i = 0; i < strLength; i += length) {
            result[j++] = inputString.substring(i, Math.min(i + length, strLength));
        }
        return result;
    }


    public static String deleteSubstring(String str, String startStr, String endStr) {
        String pattern = Pattern.quote(startStr) + "(.*?)" + Pattern.quote(endStr);
        return str.replaceAll(pattern, "");
    }

}
