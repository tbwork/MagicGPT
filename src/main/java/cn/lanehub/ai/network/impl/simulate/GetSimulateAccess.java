package cn.lanehub.ai.network.impl.simulate;

import cn.lanehub.ai.network.IAccess;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetSimulateAccess implements IAccess {


    public static final GetSimulateAccess INSTANCE = new GetSimulateAccess();

    private GetSimulateAccess() {
    }


    public static WebDriver getWebSingerDriver() {
        WebDriverManager.chromedriver().setup();

        //初始化一个谷歌浏览器实例，实例名称叫driver
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments("--disable-blink-features=AutomationControlled");

        // 创建 ChromeDriverService 并禁用日志记录
        ChromeDriverService service = new ChromeDriverService.Builder()
                .withSilent(true)
                .usingAnyFreePort()
                .withVerbose(false)
                .build();


        boolean flag = true;
        while (flag) {
            WebDriver driver = new ChromeDriver(service,options);
            ((JavascriptExecutor) driver).executeScript("'Object.defineProperty(navigator,\"webdriver\",{get:() => false,});'");
            flag = false;
            try {
                // get()打开一个站点
                return driver;
            } catch (Exception exception) {
                driver.quit();
                exception.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public String access(String url, Map<String, String> headers, Map<String, String> querys, String body) {
        WebDriver webSingerDriver = getWebSingerDriver();
        webSingerDriver.get(url);
        String pageSource = webSingerDriver.getPageSource();
        webSingerDriver.quit();
        return pageSource;


    }
}
