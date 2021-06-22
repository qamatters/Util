package gitHubSpecialFeature;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class executionInOpenBrowser {

    /*
    /Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --remote-debugging-port=9222 -user-data-dir=chromedata
     */

    public static void main(String[] args) {
        String osName = System.getProperty("os.name");
        String chromeDriverPathForMac = "src//main//resources//chromedriver_chrome_91//chromedrivermac";
        String chromeDriverPathForWindows = "src\\main\\resources\\chromedriver_chrome_91\\chromedriver.exe";

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", "localhost:9222");
        options.addArguments("--incognito");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        System.setProperty("webdriver.chrome.driver", chromeDriverPathForMac);
        ChromeDriver driver = new ChromeDriver(capabilities);
        driver.get("https://github.com/qamatters/KarateDemo");
        if(osName.contains("Mac")) {
            System.setProperty("webdriver.chrome.driver", chromeDriverPathForMac);
        } else {
            System.setProperty("webdriver.chrome.driver", chromeDriverPathForWindows);
        }
    }
}
