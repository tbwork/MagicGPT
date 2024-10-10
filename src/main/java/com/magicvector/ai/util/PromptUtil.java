package com.magicvector.ai.util;

import com.magicvector.ai.model.Arg;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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



    public static String argToPrompt(Arg arg){

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
