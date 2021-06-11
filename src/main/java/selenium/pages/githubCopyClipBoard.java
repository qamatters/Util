package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import selenium.core.DriverFactory;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class githubCopyClipBoard extends DriverFactory {
    public static void validateClipBoardCopy() throws InterruptedException, IOException, UnsupportedFlavorException {
        if (driver.findElement(By.cssSelector(".position-relative > .btn-primary")).isDisplayed()) {
            driver.findElement(By.cssSelector(".position-relative > .btn-primary")).click();
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div:nth-child(2) > .input-group .octicon-clippy")));
            if (driver.findElement(By.cssSelector("div:nth-child(2) > .input-group .octicon-clippy")).isDisplayed()) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                driver.findElement(By.cssSelector("div:nth-child(2) > .input-group .octicon-clippy")).click();
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
