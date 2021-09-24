package selenium.locators.gitbub;

import selenium.core.elements.Element;

public class githubRepoPage {
    public static Element buttonElement = new Element(".position-relative > .btn-primary",Element.LocatorType.CSS_SELECTOR);
    public static Element clipButtonElement = new Element("div:nth-child(2) > .input-group .octicon-clippy",Element.LocatorType.CSS_SELECTOR);
    public static Element allOptionsInRepoPage = new Element("//*[@id='js-repo-pjax-container']/div[1]/nav/ul/li[*]/a/span[1]", Element.LocatorType.XPATH);
    public static Element blogPage = new Element("//img[@alt = 'Automation']", Element.LocatorType.XPATH);
}
