package selenium.pages.gitHub;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import selenium.core.drivers.DriverFactory;
import selenium.core.elements.Element;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import static selenium.locators.gitbub.githubRepoPage.buttonElement;
import static selenium.locators.gitbub.githubRepoPage.clipButtonElement;

public class githubCopyClipBoard extends DriverFactory {

    public static void validateClipBoardCopy() throws InterruptedException, IOException, UnsupportedFlavorException {
        if (buttonElement.isDisplayed()) {
            buttonElement.click();
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div:nth-child(2) > .input-group .octicon-clippy")));
            if (clipButtonElement.isDisplayed()) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipButtonElement.click();
                Thread.sleep(2000);
                Object dataFromCopyURLFeature = clipboard.getData(DataFlavor.stringFlavor);
                System.out.println("Copied data is " + dataFromCopyURLFeature);
                Assert.assertTrue(dataFromCopyURLFeature.equals("https://github.com/qamatters/KarateDemo.git"), "Validate the url");
            } else {
                Assert.fail("Clipboard option is not present");
            }
        } else {
            Assert.fail("Code copy button is not preent");
        }
    }
}
