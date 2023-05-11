package cn.lanehub.ai.util;

import cn.lanehub.ai.model.Arg;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PromptUtil {


    public static String joinPrompt(String[] prompts, String delimiter) {
        if (prompts == null || prompts.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(prompts[0]);
        for (int i = 1; i < prompts.length; i++) {
            sb.append(delimiter).append(prompts[i]);
        }
        return sb.toString();
    }


    public static String formatPrompt(String template, String... args) {
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


    public static String parseResource(String promptResourceName) {
        Map<String, String> promptMap = new HashMap<>();
        String language = null;
        StringBuilder promptBuilder = new StringBuilder();
        try (
                InputStream inputStream = PromptUtil.class.getClassLoader().getResourceAsStream(promptResourceName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                promptBuilder.append(line).append(System.lineSeparator());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return promptBuilder.toString();
    }



    public static String argToString(Arg arg){

        String requireDesc = arg.isRequired() ? "不可为空":"可空";

        return "$"+arg.getName()+
                (StringUtil.isEmpty(arg.getDescription().trim())? "" : "("+requireDesc+"。"+arg.getDescription().trim()+")");

    }

    public static String join(String[] strings, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]);
            if (i < strings.length - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }


    public static String readTestResourceFile(String fileName) {
        return readResourceFileByPath("src/test/resources/"+fileName);
    }

    public static String readResourceFile(String fileName) {
        return readResourceFileByPath("src/java/resources/"+fileName);
    }


    private static String readResourceFileByPath(String relativePath) {
        try{
            String filePath = relativePath;
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
            String fileContent = new String(fileBytes, StandardCharsets.UTF_8);
            return fileContent;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }




}
