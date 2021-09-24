package selenium.core.baseHelper;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import selenium.core.drivers.DriverFactory;


public class seleniumBase extends DriverFactory {

    public static void launchGoogleHomePage(String url, String pageTitle) {
        driver.navigate().to(url);
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        System.out.println(driver.getTitle());
       wait.until(ExpectedConditions.titleIs(pageTitle));

    }

    public static void launchWebPage(String url) {
        driver.navigate().to(url);
        driver.manage().window().maximize();
       Assert.assertTrue(driver.getTitle().equalsIgnoreCase("GitHub - qamatters/KarateDemo: API testing with Karate Framework - Framework Demo"));
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".position-relative > .btn-primary")));
    }

    public static void highlightElementForFewSeconds(int duration) throws InterruptedException {
        JavascriptExecutor js;
        js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.id("sibling-2.3"));
        String original_style = element.getAttribute("style");
        js.executeScript(
                "arguments[0].setAttribute(arguments[1], arguments[2])",
                element,
                "style",
                "border: 2px solid red; border-style: dashed;");

        if (duration > 0) {
            Thread.sleep(duration * 1000);
            js.executeScript(
                    "arguments[0].setAttribute(arguments[1], arguments[2])",
                    element,
                    "style",
                    original_style);
        }
    }
}
