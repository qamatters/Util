package selenium.core.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverFactory {
    public static WebDriver driver;
    private static String chromeDriverPath = "src\\main\\resources\\chromedriver_chrome_91\\chromedriver.exe";

    public void DriverConfiguration() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();
    }

    public void QuitDriver() {
        driver.quit();
    }
}
