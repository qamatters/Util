package selenium.core.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverFactory {
    public static WebDriver driver;
    private static String chromeDriverPathForMac = "src//main//resources//chromedriver_chrome_91//chromedrivermac";
    private static String chromeDriverPathForWindows = "src\\main\\resources\\chromedriver_chrome_91\\chromedriver.exe";

    public void DriverConfiguration() {
        String osName = System.getProperty("os.name");
        System.out.println(osName );
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        if(osName.contains("Mac")) {
            System.setProperty("webdriver.chrome.driver", chromeDriverPathForMac);
        } else {
            System.setProperty("webdriver.chrome.driver", chromeDriverPathForWindows);
        }

        driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();
    }

    public void QuitDriver() {
        driver.quit();
    }
}
