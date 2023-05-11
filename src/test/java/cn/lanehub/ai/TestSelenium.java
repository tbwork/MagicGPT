package cn.lanehub.ai;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSelenium {


    public static void main(String[] args) {
        // 下载和安装ChromeDriver
        WebDriverManager.chromedriver().setup();

        // 创建Chrome浏览器的WebDriver实例
        WebDriver driver = new ChromeDriver();

        // 访问百度首页
        driver.get("https://www.baidu.com/");

        // 在搜索框中输入关键字并搜索
        driver.findElement(By.id("kw")).sendKeys("Selenium WebDriver");
        driver.findElement(By.id("su")).click();

        // 关闭浏览器
        driver.quit();
    }

}
