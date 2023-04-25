package org.mb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CategoryPage extends BasePage {

    // stage shadow root node
    private static final By STAGE_SHADOW_HOST = By.cssSelector("owc-stage[class*='owc-image-stage-host']");

    public static final String title = "A-Class Hatchback | Mercedes-Benz";

    //Build your car link
    private static final By BUILD_CAR_LINK =  By.linkText("Build your car");

    /**
     * Constructor for CategoryPage
     * @param driver
     */
    public CategoryPage(final WebDriver driver) {
        super(driver);
    }

    /**
     * Clicks on Build your car button
     * @return CarConfiguratorPage
     */
    public CarConfiguratorPage buildYourCar(){
        moveToAndClick(findShadowElement(STAGE_SHADOW_HOST,BUILD_CAR_LINK));
        return new CarConfiguratorPage(driver);
    }
}
