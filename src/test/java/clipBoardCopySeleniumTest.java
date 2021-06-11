import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import selenium.core.DriverFactory;
import selenium.core.seleniumBase;
import selenium.pages.githubCopyClipBoard;

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
