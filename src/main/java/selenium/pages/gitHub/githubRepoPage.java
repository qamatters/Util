package selenium.pages.gitHub;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.Coords;
import selenium.core.drivers.DriverFactory;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

    public static void waitForElementToDisappear() {
        buttonElement.click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div:nth-child(2) > .input-group .octicon-clippy")));
        if(clipButtonElement.isDisplayed()) {
            buttonElement.click();
        }
    }

    public static Screenshot initiateImageCompare() {
        return new AShot().takeScreenshot(driver);

    }

    public static Screenshot initiateImageCompareWithSpecificElement(String locator) {
        return new AShot().takeScreenshot(driver, DriverFactory.driver.findElements(By.xpath(locator)));

    }


    public static Screenshot initiateImageCompareWithIgnoringElement() {
        return new AShot()
                .addIgnoredElement(By.xpath("//div[@class='news-tick-wrap']")) // ignored element(s)
                .takeScreenshot(driver);
    }

    public static Set<Coords> getCoords( ) {
        Set<Coords> ignoredCoords = new HashSet<>();
            org.openqa.selenium.Point point = driver.findElement(By.xpath("//div[@class='news-tick-wrap']")).getLocation();
            Dimension dimension = driver.findElement(By.xpath("//div[@class='news-tick-wrap']")).getSize();
            ignoredCoords.add(new Coords(point.getX(), point.getY(), dimension.getWidth(), dimension.getHeight()));
            return ignoredCoords;
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
