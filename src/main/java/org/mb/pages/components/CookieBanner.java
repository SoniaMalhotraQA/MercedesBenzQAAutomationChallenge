package org.mb.pages.components;

import org.mb.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CookieBanner extends BasePage {

    //cookie banner shadow host
    private static final By COOKIE_SHADOW_HOST = By.cssSelector("cmm-cookie-banner[class*='hydrated']");

    //Accept all button
    private static final By ACCEPT_ALL_BUTTON = By.cssSelector("button[class*='wb-button--accept-all']");

    /**
     * Constructor for CookieBanner
     * @param driver
     */
    public CookieBanner(final WebDriver driver) {
        super(driver);
    }

    /**
     * The method accepts all cookies
     * @return CookieBanner
     */
    public CookieBanner acceptAllCookies(){
        moveToAndClick(findShadowElement(COOKIE_SHADOW_HOST,ACCEPT_ALL_BUTTON));
        return this;
    }
}
