package com.magicvector.ai.util;

import java.math.BigInteger;
import java.util.UUID;

public class IDUtil {


    private static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = CHARACTERS.length();

    /**
     * @return 输出一个22位长度的随机字符串，且不会重复
     */
    public static String getUniqueId() {
        UUID uuid = UUID.randomUUID();
        BigInteger number = new BigInteger(uuid.toString().replaceAll("-", ""), 16);
        StringBuilder sb = new StringBuilder();
        while (number.compareTo(BigInteger.ZERO) > 0) {
            BigInteger remainder = number.mod(BigInteger.valueOf(BASE));
            sb.insert(0, CHARACTERS.charAt(remainder.intValue()));
            number = number.divide(BigInteger.valueOf(BASE));
        }

        String result = sb.toString();


        return padStringWithZeros(result);
    }


    public static String padStringWithZeros(String input) {
        int desiredLength = 22;
        if (input.length() >= desiredLength) {
            return input; // 输入字符串已经达到或超过目标长度，无需填充
        }

        int paddingLength = desiredLength - input.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paddingLength; i++) {
            sb.append('0');
        }
        sb.append(input);

        return sb.toString();
    }


}
