package com.magicvector.ai.util;
import com.magicvector.ai.exceptions.MessageStreamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class IOUtil {

    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    public static boolean isUrlAccessible(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            return false;
        }
    }


    public static void writeToOutpuStream(String targetString, OutputStream outputStream){

        try{
            outputStream.write(targetString.getBytes());
        }
        catch (Exception e){
            throw new MessageStreamException(e);
        }

    }

    public static File getFileByURL(String url){
        boolean urlAccessible = IOUtil.isUrlAccessible(url);
        File file = null;
        if(urlAccessible){
            String newUrl = url.split("[?]")[0];
            String[] suffix = newUrl.split("/");
            //得到最后一个分隔符后的名字
            String fileName = suffix[suffix.length - 1];
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try{
                //创建临时文件
                file = File.createTempFile("ocr",fileName);
                URL urlFile = new URL(url);
                inputStream = urlFile.openStream();
                outputStream = new FileOutputStream(file);
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead=inputStream.read(buffer,0,8192))!=-1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    if (null != outputStream) {
                        outputStream.close();
                    }
                    if (null != inputStream) {
                        inputStream.close();
                    }
                } catch (Exception ex) {
                    logger.error("Failed to file close: {}", ex.getMessage());
                }
            }
        }
        return file;
    }

}
