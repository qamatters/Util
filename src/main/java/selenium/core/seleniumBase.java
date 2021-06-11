package selenium.core;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class seleniumBase extends DriverFactory {

    public static void launchWebPage(String url) {
        driver.navigate().to(url);
        driver.manage().window().maximize();
        Assert.assertTrue(driver.getTitle().equalsIgnoreCase("GitHub - qamatters/KarateDemo: API testing with Karate Framework - Framework Demo"));
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".position-relative > .btn-primary")));
    }


}
