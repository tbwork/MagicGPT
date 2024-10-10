package com.magicvector.ai.util;

import java.util.regex.Matcher;
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


    public static String formatString(String template, String... args) {
        // 使用正则表达式匹配{}占位符
        Pattern pattern = Pattern.compile("\\{\\}");
        Matcher matcher = pattern.matcher(template);

        // 统计占位符的数量
        int count = 0;
        while (matcher.find()) {
            count++;
        }

        // 校验占位符数量是否和输入参数一致
        if (count != args.length) {
            throw new IllegalArgumentException("Invalid argument count: " + args.length + ", expected: " + count);
        }

        // 依次替换占位符
        StringBuilder sb = new StringBuilder();
        matcher.reset();
        int index = 0;
        int argIndex = 0;
        while (matcher.find()) {
            sb.append(template.substring(index, matcher.start()));
            sb.append(args[argIndex]);
            argIndex++;
            index = matcher.end();
        }
        sb.append(template.substring(index));
        return sb.toString();
    }
}
