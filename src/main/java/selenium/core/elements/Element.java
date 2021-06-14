package selenium.core.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.core.drivers.DriverFactory;

public class Element extends DriverFactory {
    protected String locator = null;
    public By by = null;

    public Element(By by) {
        this.by = by;
    }

    public Element(String locator, Element.LocatorType locatorType) {
        this.locator = locator;
        this.by = this.getLocatorBy(locator, locatorType);
    }

    public WebElement getElement() {
        return driver.findElement(this.by);
    }

    public boolean isDisplayed() {
        return this.getElement().isDisplayed();
    }

    public void click() {
        this.getElement().click();
    }

    private By getLocatorBy(String locator, Element.LocatorType locatorType) {
        switch (locatorType) {
            case ID:
                return By.id(locator);
            case Class_Name:
                return By.className(locator);
            case Link_Text:
                return By.linkText(locator);
            case NAME:
                return By.name(locator);
            case XPATH:
                return By.xpath(locator);
            case CSS_SELECTOR:
                return By.cssSelector(locator);
            case TAG_NAME:
                return By.tagName(locator);
            default:
                return By.xpath(locator);
        }
    }

    public static enum LocatorType {
        ID,
        NAME,
        Class_Name,
        Link_Text,
        XPATH,
        CSS_SELECTOR,
        TAG_NAME;

        LocatorType() {

        }

    }


}
