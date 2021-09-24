package gitHubSpecialFeature;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;

public class executionInOpenBrowser {

    /*
    /Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --remote-debugging-port=9222 -user-data-dir=chromedata
     */

    /*
    For windows:

    C:\Program Files\Google\Chrome\Application>
    chrome.exe --remote-debugging-port=9222 --user-data-dir=C:\Users\deepak.mathpal\Documents\chromedata
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
        if(osName.contains("Mac")) {
            System.setProperty("webdriver.chrome.driver", chromeDriverPathForMac);
            ChromeDriver driver = new ChromeDriver(capabilities);
            System.setProperty("webdriver.chrome.driver", chromeDriverPathForMac);
            driver.get("https://github.com/qamatters/KarateDemo");
        } else {
            System.setProperty("webdriver.chrome.driver", chromeDriverPathForWindows);
            ChromeDriver driver = new ChromeDriver(capabilities);
            System.setProperty("webdriver.chrome.driver", chromeDriverPathForWindows);
//            driver.get("https://github.com/qamatters/KarateDemo");
//            driver.findElement(By.xpath("//a[normalize-space()='Go to file']")).click();
            System.out.println("Printing all links");
//            List<WebElement> allLinks = driver.findElements(By.xpath("//li[@class='css-truncate css-truncate css-truncate-overflow']/a[@href]"));
//            System.out.println(driver.getCurrentUrl());
//            System.out.println(allLinks.size());

            String item = driver.findElement(By.xpath("//span[contains(@data-content,'Issues')]")).getText();
            System.out.println(item);
            driver.findElement(By.xpath("//span[normalize-space()='Jenkinsfile']")).click();
        }
    }
}
