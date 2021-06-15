package selenium.pages.gitHub;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import selenium.core.drivers.DriverFactory;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static selenium.locators.gitbub.githubRepoPage.*;

public class githubRepoPage extends DriverFactory {


    public static List<String>  AllOptionsInRepoPage() throws InterruptedException {
        List<String> expectedAllOptions = new LinkedList<>();
        Thread.sleep(2000);
     List<WebElement> elements  = allOptionsInRepoPage.getElements();
     System.out.println("size: " + elements.size());
     for(WebElement element : elements) {
         if(element.isDisplayed()) {
             String elementText = element.getText();
             expectedAllOptions.add(elementText);
         }
     }
     return expectedAllOptions;
    }


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
