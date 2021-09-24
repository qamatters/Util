package gitHubSpecialFeature;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import selenium.core.baseHelper.seleniumBase;
import selenium.core.drivers.DriverFactory;

public class highlightElement {
    DriverFactory driver = new DriverFactory();

    @BeforeTest
    public void initialize() {
        driver.DriverConfiguration();
        seleniumBase.launchWebPage("http://the-internet.herokuapp.com/large");
    }

    @Test
    public void testDefaultOptions() throws InterruptedException {
        seleniumBase.highlightElementForFewSeconds(3);

        Thread.sleep(1000 * 5);
    }

    @AfterTest
    public void quit() {
        driver.QuitDriver();
    }
}
