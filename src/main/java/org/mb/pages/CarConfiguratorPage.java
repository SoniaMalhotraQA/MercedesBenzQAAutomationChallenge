package org.mb.pages;

import org.mb.utils.ConfigLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.List;

public class CarConfiguratorPage extends BasePage{

    public static final String title = "Configurator";

    //Main shadow host
    private static final By MAIN_SHADOW_HOST = By.cssSelector("owcc-car-configurator[class='webcomponent aem-GridColumn aem-GridColumn--default--12']");

    //Motor Heading
    private static final By MOTOR_HEADING = By.cssSelector("div[class='cc-motorization-heading']");

    //Multiselect filter shadow host
    private static final By FILTER_SHADOW_HOST = By.cssSelector("ccwb-multi-select[class='cc-motorization-filters-primary-filters--multi-select wb-multi-select hydrated']");

    //Filter dropdown
    private static final By FILTER_DROPDOWN = By.cssSelector("button div[class='label']");

    //Fuel type
    public static final String FUEL_TYPE= "Fuel type";

    //Checkbox shadow root
    private static final By CHECKBOX_SHADOW_HOST = By.cssSelector("ccwb-checkbox[class*='wb-checkbox']");

    //Diesel checkbox
    private static final By DIESEL_CHECKBOX =  By.cssSelector("label input[name='Diesel']");

    //Result Heading
    private static final By RESULT_HEADING = By.cssSelector("ccwb-heading[class='wb-padding-bottom-xs wb-heading hydrated']");

    //Price Value
    private static final By PRICE_VALUE_SPAN = By.cssSelector("ccwb-text span[class*='cc-motorization-header__price--with-environmental-hint']");

    /**
     * Constructor for CarConfiguratorPage
     * @param driver
     */
    public CarConfiguratorPage(final WebDriver driver) {
        super(driver);
    }

    /**
     * Scrolls to filters
     * @return CarConfiguratorPage
     * @throws InterruptedException
     */
    public CarConfiguratorPage scrollToFilters() throws InterruptedException {
        scrollIntoView(findShadowElement(MAIN_SHADOW_HOST,MOTOR_HEADING));
        //scrollIntoView(findShadowElement(MAIN_SHADOW_HOST,MOTOR_HEADING));
        return this;
    }

    /**
     * Finds a filter dropdown by text and clicks it
     * @param text
     * @return CarConfiguratorPage
     * @throws InterruptedException
     */
    public CarConfiguratorPage clickFilterDropdown(final String text) throws InterruptedException {
        WebElement element = findNestedShadowElementByText(MAIN_SHADOW_HOST,FILTER_SHADOW_HOST,FILTER_DROPDOWN,text);
        Wait fluentWait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(ConfigLoader.getInstance().getWaitInterval()))
                .pollingEvery(Duration.ofSeconds(3))
                .ignoring(ElementClickInterceptedException.class);
        fluentWait.until(d -> click(element));
        //scrollIntoViewAndClick(findNestedShadowElementByText(MAIN_SHADOW_HOST,FILTER_SHADOW_HOST,FILTER_DROPDOWN,text));
        return this;
    }

    /**
     * Selects Diesel Fuel type checkbox from filter
     * @return CarConfiguratorPage
     */
    public CarConfiguratorPage selectDieselFuelType(){
        WebElement element = findNestedShadowElement(MAIN_SHADOW_HOST,CHECKBOX_SHADOW_HOST,DIESEL_CHECKBOX);
        Wait fluentWait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(ConfigLoader.getInstance().getWaitInterval()))
                .pollingEvery(Duration.ofSeconds(3))
                .ignoring(MoveTargetOutOfBoundsException.class);
        fluentWait.until(d -> moveTo(element));
        //moveToAndClick(findNestedShadowElement(MAIN_SHADOW_HOST,CHECKBOX_SHADOW_HOST,DIESEL_CHECKBOX));
        return this;
    }

    /**
     * Moves to Element
     * @param element
     * @return
     */
    private boolean moveTo(final WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        return true;
    }

    /**
     * Reads Result Text on page
     * @return
     */
    public String readResultText(){
        return findShadowElement(MAIN_SHADOW_HOST,RESULT_HEADING).getText();
    }

    /**
     * Reads price labels of Results
     * @return
     */
    public List<WebElement> readPriceLabels(){
        return findShadowElements(MAIN_SHADOW_HOST,PRICE_VALUE_SPAN);
    }
}
