package org.mb.pages;

import org.mb.pages.components.CookieBanner;
import org.mb.pages.components.MenuBar;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage{

    //Home page relative path
    private static final String pagePath = "/";

    //Home Page Title constant
    public static final String title = "Mercedes-Benz Passenger Cars";

    //Menu Bar component
    private MenuBar menuBar;

    //CookieBanner component
    private CookieBanner cookieBanner;

    /**
     * Constructor for Homepage
     * @param driver
     */
    public HomePage(WebDriver driver) {
        super(driver);
        menuBar = new MenuBar(driver);
        cookieBanner = new CookieBanner(driver);
    }

    /**
     * Loads Home page
     * @return
     */
    public HomePage load(){
        loadPage(pagePath);
        return this;
    }

    /**
     * Getter for menubar
     * @return
     */
    public MenuBar getMenuBar() {
        return menuBar;
    }

    /**
     * Getter for Cookie banner
     * @return
     */
    public CookieBanner getCookieBanner() {
        return cookieBanner;
    }
}
