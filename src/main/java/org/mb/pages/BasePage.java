package org.mb.pages;

import org.mb.utils.ConfigLoader;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    //WebDriver Reference
    protected WebDriver driver;

    //WebDriver Wait reference
    protected WebDriverWait webDriverWait;

    //Website base Url
    private String baseURL = ConfigLoader.getInstance().getBaseUrl();

    /**
     * Constructor for BasePage
     * @param driver
     */
    public BasePage(final WebDriver driver){
        this.driver = driver;
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Gets page title
     * @return page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Finds an element by test from a list of web elements
     * @param text
     * @param elements
     * @return WebElement
     */
    private static WebElement findElementByText(final String text, final List<WebElement> elements) {
        WebElement element = elements.stream().
                filter(e -> e.getText().equalsIgnoreCase(text)).findFirst().
                orElseThrow(() -> new NoSuchElementException("Element not found with text" + text));
        return element;
    }

    /**
     * Finds Webelement by Locator
     * @param locator
     * @return WebElement
     */
    public WebElement findElement(final By locator){
        waitForPresence(locator);
        return driver.findElement(locator);
    }

    /**
     * Finds a shadowed element which is located inside a shadow host
     * @param shadowHost
     * @param target
     * @return WebElement
     */
    public WebElement findShadowElement(final By shadowHost, final By target) {
        SearchContext searchContext = findElement(shadowHost).getShadowRoot();
        waitToBeVisible(searchContext,target);
        return searchContext.findElement(target);
    }

    /**
     * Finds shadowed elements which are located inside a shadow host
     * @param shadowHost
     * @param target
     * @return List<WebElement>
     */
    public List<WebElement> findShadowElements(final By shadowHost, final By target) {
        SearchContext searchContext = findElement(shadowHost).getShadowRoot();
        waitToBeVisible(searchContext,target);
        return searchContext.findElements(target);
    }

    /**
     * Finds a shadowed element which is present inside two nested shadow hosts
     * @param shadowHost
     * @param childShadowHost
     * @param target
     * @return WebElement
     */
    public WebElement findNestedShadowElement(final By shadowHost, final By childShadowHost, final By target) {
        return findShadowElement(shadowHost,childShadowHost).getShadowRoot().findElement(target);
    }

    /**
     * Finds shadowed elements which are present inside two nested shadow hosts
     * @param shadowHost
     * @param childShadowHost
     * @param target
     * @return List<WebElement>
     */
    public List<WebElement> findNestedShadowElements(final By shadowHost, final By childShadowHost, final By target) {
        SearchContext nestedSearchContext = findShadowElement(shadowHost,childShadowHost).getShadowRoot();
        waitToBeVisible(nestedSearchContext,target);
        return nestedSearchContext.findElements(target);
    }

    /**
     * Finds a shadowed element by text which is located inside a shadow host
     * @param shadowHost
     * @param target
     * @param text
     * @return WebElement
     */
    public WebElement findShadowElementByText(final By shadowHost, final By target, final String text){
        List<WebElement> elements = findShadowElements(shadowHost,target);
        WebElement element = findElementByText(text, elements);
        return element;
    }

    /**
     * Finds a shadowed element by text which is located inside two nested shadow hosts
     * @param shadowHost
     * @param childShadowHost
     * @param target
     * @param text
     * @return
     */
    public WebElement findNestedShadowElementByText(final By shadowHost, final By childShadowHost, final By target, final String text) {
        List<WebElement> webElements = findNestedShadowElements(shadowHost,childShadowHost,target);
        WebElement element = findElementByText(text, webElements);
        return element;
    }

    /**
     * Moves to the Webelement and clicks it with explicit waits
     * @param element
     */
    public void moveToAndClick(final WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        waitToBeVisible(element);
        click(element);
    }

    /**
     * Scrolls to bring the Webelement into viewpoint
     * @param element
     */
    public void scrollIntoView(final WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript ("arguments[0].scrollIntoView({behaviour: 'instant', block: 'center', inline: 'nearest'})", element);
        waitToBeVisible(element);
    }

    /**
     * Loads a page
     * @param pagePath
     */
    public void loadPage(final String pagePath){
        driver.get(baseURL+pagePath);
    }

    /**
     * Waits for presence of an element
     * @param locator
     */
    public void waitForPresence(final By locator){
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Waits for a webelement to be clickable
     * @param webElement
     * @return
     */
    public WebElement waitToBeClickable(final WebElement webElement){
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    /**
     * Waits for a webelement to be visible
     * @param webElement
     * @return
     */
    public WebElement waitToBeVisible(final WebElement webElement){
        return webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
    }

    /**
     * Fluently waits for a webelement which is present inside a shadow root
     * @param searchContext
     * @param locator
     */
    private void waitToBeVisible(final SearchContext searchContext, final By locator){
        Wait<SearchContext> fluentWait= new FluentWait<SearchContext>(searchContext)
                .withTimeout(Duration.ofSeconds(ConfigLoader.getInstance().getWaitInterval()))
                .pollingEvery(Duration.ofSeconds(ConfigLoader.getInstance().getFluentWaitPollingInterval()))
                .ignoring(NoSuchElementException.class);
        fluentWait.until(sc -> sc.findElement(locator).isDisplayed());
    }

    /**
     * Waits for a webelement to be clickable and click it.
     * @param webElement
     * @return
     */
    public boolean click(final WebElement webElement){
        waitToBeClickable(webElement).click();
        return true;
    }
}
