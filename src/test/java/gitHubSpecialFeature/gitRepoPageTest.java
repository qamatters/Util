package gitHubSpecialFeature;

import org.apache.commons.collections4.CollectionUtils;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import selenium.core.baseHelper.seleniumBase;
import selenium.core.drivers.DriverFactory;
import selenium.pages.gitHub.githubRepoPage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class gitRepoPageTest {
    DriverFactory driver = new DriverFactory();

    @BeforeTest
    public void initialize() {
        driver.DriverConfiguration();
        seleniumBase.launchWebPage("https://github.com/qamatters/KarateDemo");
    }

    @Test
    public void testDefaultOptions() throws InterruptedException {
    List<String> expectedOptions = new LinkedList<>();
    expectedOptions.add("Code");
    expectedOptions.add("Issues");
    expectedOptions.add("Pull requests");
    expectedOptions.add("Actions");
    expectedOptions.add("Insights");
    expectedOptions.add("Projects");
    expectedOptions.add("Wiki");
    expectedOptions.add("Security");
    expectedOptions.add("Insights");
//  expectedOptions.add("Settings");
    System.out.println("1: " + expectedOptions);
    List<String> actualOptions = githubRepoPage.AllOptionsInRepoPage();
    System.out.println("2: " + actualOptions);
    boolean result = CollectionUtils.isEqualCollection(expectedOptions,actualOptions);
    System.out.println(result);
    }
    @AfterTest
    public void quit() {
        driver.QuitDriver();
    }
}


