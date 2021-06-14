package selenium.core.elements;

import org.openqa.selenium.By;
import selenium.core.elements.Element;

public class TextField extends Element {
    public TextField (By by) {
        super(by);
    }
    public TextField (String locator, LocatorType locatorType) {
        super(locator, locatorType);
    }

    public void clearAndType(String text) {
        this.getElement().clear();
        this.getElement().sendKeys(text);
    }

    public void type (String text) {
        this.getElement().sendKeys(text);
    }

}
