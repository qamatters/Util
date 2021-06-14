package gitHubSpecialFeature;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import selenium.core.drivers.DriverFactory;
import selenium.core.baseHelper.seleniumBase;
import selenium.pages.gitHub.githubCopyClipBoard;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;


public class clipBoardCopySeleniumTest {
    DriverFactory driver = new DriverFactory();

    @BeforeTest
    public void initialize() {
        driver.DriverConfiguration();
    }

    @Test
    public void testClipBoardFeature() throws IOException, InterruptedException, UnsupportedFlavorException {
        seleniumBase.launchWebPage("https://github.com/qamatters/KarateDemo");
        githubCopyClipBoard.validateClipBoardCopy();

    }

    @AfterTest
    public void quit() {
        driver.QuitDriver();

    }
}
