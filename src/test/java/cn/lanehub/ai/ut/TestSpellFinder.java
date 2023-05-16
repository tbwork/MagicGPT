package cn.lanehub.ai.ut;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestSpellFinder {

    public static List<String> findSubstrings(String str) {
        List<String> substrings = new ArrayList<>();
        Pattern pattern = Pattern.compile("#@.*?#@");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            substrings.add(matcher.group());
        }
        return substrings;
    }

    public static void main(String[] args) {
        String str = "#@123#@adsfasdf#@456#@";
        List<String> substrings = findSubstrings(str);
        for (String substring : substrings) {
            System.out.println(substring);
        }
    }
}
