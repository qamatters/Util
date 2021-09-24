package gitHubSpecialFeature;

import org.testng.annotations.*;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.coordinates.Coords;
import selenium.core.baseHelper.seleniumBase;
import selenium.core.drivers.DriverFactory;
import selenium.pages.gitHub.githubRepoPage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Set;

import static java.lang.Thread.sleep;


public class compareImage {
    public static  final String actualDir = "target/screenshot/actual/";
    public static  final String expectedDir = "target/screenshot/expectedDir/";
    public static  final String markedImagesDir = "target/screenshot/markedImages/";
    DriverFactory driver = new DriverFactory();

    @BeforeClass
    public void initialize() {
        driver.DriverConfiguration();
    }

    @Test
    public void testGmailAboutPage(Method method) throws Exception {
        seleniumBase.launchGoogleHomePage("https://www.google.com/intl/en-GB/gmail/about/#", "Gmail: Free, Private & Secure Email | Google Workspace");
        String actualImage =method.getName();
        String expectedImage =  method.getName();
        String diffImage =  method.getName();
        Robot robot = new Robot();

        robot.mouseMove(0, 0);
        Screenshot actualScreenshot = githubRepoPage.initiateImageCompare();
        File actualFile = new File(actualDir + actualImage + ".png");
        ImageIO.write(actualScreenshot.getImage(), "png", actualFile);
        Screenshot expectedScreenshot = new Screenshot(ImageIO.read(new File(expectedDir + expectedImage+ ".png")));
        ImageDiff diff = new ImageDiffer().makeDiff(expectedScreenshot, actualScreenshot);
        int size = diff.getDiffSize();
        if (size != 0) {
            File diffFile = new File(markedImagesDir +diffImage+ ".png");
            ImageIO.write(diff.getMarkedImage(), "png", diffFile);
            throw new Exception("There are some bugs on the page");
        }
    }

    @Test
    public void testSpecificFieldsFromBlog(Method method) throws Exception {
        seleniumBase.launchGoogleHomePage("https://testwithease.blogspot.com/", "Automation");
        Thread.sleep(1000*5);
        String actualImage =   method.getName();
        String expectedImage =   method.getName();
        String diffImage =  method.getName();
        Robot robot = new Robot();

        robot.mouseMove(0, 0);
        Screenshot actualScreenshot = githubRepoPage.initiateImageCompareWithSpecificElement("(//div[@class='widget LinkList'])[4]");
        File actualFile = new File(actualDir + actualImage + ".png");
        ImageIO.write(actualScreenshot.getImage(), "png", actualFile);
        Screenshot expectedScreenshot = new Screenshot(ImageIO.read(new File(expectedDir + expectedImage+ ".png")));
        ImageDiff diff = new ImageDiffer().makeDiff(expectedScreenshot, actualScreenshot);
        int size = diff.getDiffSize();
        if (size != 0) {
            File diffFile = new File(markedImagesDir +diffImage+ ".png");
            ImageIO.write(diff.getMarkedImage(), "png", diffFile);
            throw new Exception("There are some bugs on the page");
        }
    }

    @Test
    public void testBlogHomePageIgnoringDynamicContent(Method method) throws Exception {

        seleniumBase.launchGoogleHomePage("https://testwithease.blogspot.com/", "Automation");
        sleep(1000*10);

        String actualImage =   method.getName();
        String expectedImage =   method.getName();
        String diffImage =  method.getName();

        Robot robot = new Robot();
        robot.mouseMove(0, 0);
        Screenshot actualScreenshot = githubRepoPage.initiateImageCompareWithIgnoringElement();

        Set<Coords> ignoredCoords = githubRepoPage.getCoords();
        actualScreenshot.setIgnoredAreas(ignoredCoords);
        actualScreenshot.setIgnoredAreas(actualScreenshot.getIgnoredAreas());

        File actualFile = new File(actualDir + actualImage + ".png");
        ImageIO.write(actualScreenshot.getImage(), "png", actualFile);

        Screenshot expectedScreenshot = new Screenshot(ImageIO.read(new File(expectedDir + expectedImage+ ".png")));
        expectedScreenshot.setIgnoredAreas(actualScreenshot.getIgnoredAreas());

        ImageDiff diff = new ImageDiffer().makeDiff(expectedScreenshot,actualScreenshot);
        int size = diff.getDiffSize();

        if (size != 0) {
            File diffFile = new File(markedImagesDir +diffImage+ ".png");
            ImageIO.write(diff.getMarkedImage(), "png", diffFile);
            throw new Exception("There are some bugs on the page");
        }
    }

    @AfterClass
    public void quit() {
        driver.QuitDriver();
    }
}
