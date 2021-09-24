package gitHubSpecialFeature;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import selenium.core.baseHelper.seleniumBase;
import selenium.core.drivers.DriverFactory;
import selenium.pages.gitHub.githubRepoPage;


public class closeDialog {

    DriverFactory driver = new DriverFactory();

    @BeforeTest
    public void initialize() {
        driver.DriverConfiguration();
        seleniumBase.launchWebPage("https://github.com/qamatters/KarateDemo");
    }

    @Test
    public void testDefaultOptions() throws InterruptedException {
        githubRepoPage.waitForElementToDisappear();
    }
    @AfterTest
    public void quit() {
        driver.QuitDriver();
    }

}