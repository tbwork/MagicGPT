package cn.lanehub.ai.core.view.parser.impl;

import cn.lanehub.ai.core.view.parser.IMediaParser;
import cn.lanehub.ai.util.NetUtil;
import cn.lanehub.ai.util.PromptUtil;
import cn.lanehub.ai.util.StringUtil;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;

/**
 * 图片解析器
 */
public class ImageParser implements IMediaParser {

    private static final Logger logger = LoggerFactory.getLogger(ImageParser.class);

    /**
     *
     * 将指定url的图片转变为文字信息。
     * @param url
     * @return 解析文字内容
     */
    @Override
    public String parse(String url) {
        return StringUtil.formatString("[IMG::{}]", this.doParse(url));
    }

    private String doParse(String url){
        File imageFile = NetUtil.getFileByURL(url);
        ITesseract instance = new Tesseract();
        // 获取语言库的路径
        instance.setDatapath("src/main/resources/tessdata/");
        // 设置语言为中文， 中+英+数字
        instance.setLanguage("chi_sim+chi_tra+eng+osd");
        String result = null;
        try {
            long startTime = System.currentTimeMillis();
            // 执行OCR操作
            result = instance.doOCR(imageFile);
            long endTime = System.currentTimeMillis();
            logger.debug("本次OCR操作耗时{}ms", endTime-startTime);
        } catch (TesseractException ex) {
            logger.error("Tesseract Exception error info:{}",ex.getMessage());
        }
        return result;
    }

}
