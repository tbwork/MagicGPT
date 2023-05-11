package cn.lanehub.ai;

import cn.lanehub.ai.core.view.parser.impl.ImageParser;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.tbwork.anole.loader.annotion.AnoleConfigLocation;

@AnoleConfigLocation()
public class TestImageParser {


    public static void main(String[] args) {
        ImageParser imageParser = new ImageParser();
        String parse1 = imageParser.parse("https://access-wfile.yuanling.com/yl-static/buyer-safe.png");
        System.out.println(parse1);

        String parse2 = imageParser.parse("https://pub-img-wfile.yuanling.com/36/14/14/21/72/f0421a8f60637d442e2bb767633cd4e3ac06ca14c305334e5b1552d8.png?x-oss-process=style/original_yl_watermark");
        System.out.println(parse2);

        String parse3 = imageParser.parse("https://pub-img-wfile.yuanling.com/40/30/90/6/15/59b2d98cf7d0e4f49544e62360647465637271a9e37bdf121a77d168.jpg?x-oss-process=style/original_yl_watermark");
        System.out.println(parse3);

        String parse4 = imageParser.parse("https://pub-img-wfile.yuanling.com/40/39/88/29/48/94dc4db40e633a806ca3d18d2703f56127093233b40443e8699c4452.jpg?x-oss-process=style/original_yl_watermark");
        System.out.println(parse4);

        String parse5 = imageParser.parse("https://pub-img-wfile.yuanling.com/26/7/76/47/81/a43bbb73626befc6b8f599156264a012c965b160392aa4df1d0d8ec2.jpeg?x-oss-process=style/original_yl_watermark");
        System.out.println(parse5);
    }

}
