package org.mb.pages.components;

import org.mb.pages.BasePage;
import org.mb.pages.CategoryPage;
import org.openqa.selenium.*;

public class MenuBar extends BasePage {

    //Main header element
    private static final By MAIN_SHADOW_HOST = By.cssSelector("owc-header[class='webcomponent aem-GridColumn aem-GridColumn--default--12']");

    //first menu button
    private static final By MENU_BUTTON = By.cssSelector("nav button[class*='owc-header-button__iconWrapper owc-header__item-menu']");

    //Navigation Topic
    private static final By NAVIGATION_TOPIC_BUTTON = By.cssSelector("nav li button[class*='owc-header-navigation-topic__button']");

    //Flyout shadow host
    private static final By FLYOUT_SHADOW_HOST = By.cssSelector("vmos-flyout[class='webcomponent webcomponent-nested']");

    //First Flyout
    private static final By FLYOUT_LEVEL_ONE = By.cssSelector("ul li[class*='@vmos-vmos-flyout-flyout-group-item__bQahN']");

    //Second Flyout
    private static final By FLYOUT_LEVEL_TWO = By.cssSelector("owc-header-flyout ul li a[class*='@vmos-vmos-flyout-flyout-group-item__link__NeNLP']");

    //Our Models constant
    public static final String OUR_MODELS = "Our Models";

    //Hatchbacks constant
    public static final String HATCHBACKS = "Hatchbacks";

    //A class hatchbacks text
    public static final String A_CLASS_HATCHBACK = "A-Class Hatchback";
    public MenuBar(final WebDriver driver) {
        super(driver);
    }

    /**
     * The method opens the menu, if not open
     * @return Menu Bar
     */
    public MenuBar openMenu(){
        if(!isMenuAlreadyOpen()) {
            waitToBeClickable(findShadowElement(MAIN_SHADOW_HOST, MENU_BUTTON)).click();
        }
        return this;
    }

    /**
     * The method checks if the menu is already open or not
     * @return boolean
     */
    private boolean isMenuAlreadyOpen(){
        boolean result = true;
        try {
            SearchContext searchContext = findElement(MAIN_SHADOW_HOST).getShadowRoot();
            result = searchContext.findElement(NAVIGATION_TOPIC_BUTTON).isDisplayed();
        }catch (NoSuchElementException | NoSuchShadowRootException e){
            //Menu is not open
            result = false;
        }
        return result;
    }

    /**
     * Selects main menu item
     * @param text
     * @return Menu Bar
     */
    public MenuBar selectMainMenu(final String text){
        moveToAndClick(findShadowElementByText(MAIN_SHADOW_HOST, NAVIGATION_TOPIC_BUTTON,text));
        return this;
    }

    /**
     * The method selects first Menu item
     * @param text
     * @return Menu Bar
     */
    public MenuBar selectFirstMenuItem(final String text){
        moveToAndClick(findShadowElementByText(FLYOUT_SHADOW_HOST,FLYOUT_LEVEL_ONE,text));
        return this;
    }

    /**
     * The method selects second Menu item
     * @param text
     * @return Category Page
     */
    public CategoryPage selectSecondMenuItem(final String text){
        moveToAndClick(findShadowElementByText(FLYOUT_SHADOW_HOST,FLYOUT_LEVEL_TWO,text));
        return new CategoryPage(driver);
    }
}
