package org.mb.tests;

import org.mb.tests.base.BaseTest;
import org.mb.pages.CarConfiguratorPage;
import org.mb.pages.CategoryPage;
import org.mb.pages.HomePage;
import org.mb.pages.components.MenuBar;
import org.mb.utils.ConfigLoader;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

public class MbTest extends BaseTest {

    @Test
    public void testEndToEnd() throws InterruptedException, IOException {

        //Open Mercedes-benz Home Page
        HomePage homePage = new HomePage(driver).load();

        //Validate Home page is displayed
        Assert.assertEquals(homePage.getPageTitle(),HomePage.title);

        //accept all cookies
        homePage.getCookieBanner().acceptAllCookies();

        //Select OurModels -> Hatchbacks -> A Class Hatchback
        CategoryPage categoryPage = homePage.getMenuBar()
                                                        .openMenu()
                                                        .selectMainMenu(MenuBar.OUR_MODELS)
                                                        .selectFirstMenuItem(MenuBar.HATCHBACKS)
                                                        .selectSecondMenuItem(MenuBar.A_CLASS_HATCHBACK);

        //validate category page is displayed
        Assert.assertTrue(categoryPage.getPageTitle().contains(CategoryPage.title));

        //click build Your Car
        CarConfiguratorPage carConfiguratorPage = categoryPage.buildYourCar();

        //validate car configurator page is displayed
        Assert.assertTrue(carConfiguratorPage.getPageTitle().contains(CarConfiguratorPage.title));

        //Scroll To Filter -> Click Fuel Type Filter -> Select Diesel
        carConfiguratorPage
                .scrollToFilters()
                .clickFilterDropdown(CarConfiguratorPage.FUEL_TYPE)
                .selectDieselFuelType();

        //find total results
        String resultText = carConfiguratorPage.readResultText();
        int totalResults = Integer.parseInt(resultText.substring(0, resultText.indexOf(" engine variants")));

        //find all price elements
        List<WebElement> priceElements = carConfiguratorPage.readPriceLabels();

        //verify we have prices for All results
        Assert.assertEquals(priceElements.size(), totalResults);

        //store prices in a list
        List<BigDecimal> prices = new ArrayList<>();
        for (WebElement priceElement : priceElements) {
            String priceValueText = priceElement.getText();
            try {
                //un-formatted price value by removing currency symbol
                prices.add(getUnformattedPrice(priceValueText, Locale.UK));
            } catch (ParseException e) {
                throw new RuntimeException("Exception while un-formatting prices", e);
            }
        }

        //sort prices
        Collections.sort(prices, Comparator.reverseOrder());

        //Maximum price
        BigDecimal maximumPrice = prices.get(0);

        //Minimum price
        BigDecimal minimumPrice = prices.get(prices.size()-1);

        long aClassMinimumPrice = ConfigLoader.getInstance().getAClassMinimumPrice();
        long aClassMaximumPrice = ConfigLoader.getInstance().getAClassMaximumPrice();

        //Validate that minimum price is not less than 15000
        Assert.assertTrue(
                minimumPrice.compareTo(BigDecimal.valueOf(aClassMinimumPrice)) != -1,
                "Minimum price is less than "+ aClassMinimumPrice);

        //Validate that maximum price is not greater than 60000
        Assert.assertTrue(
                maximumPrice.compareTo(BigDecimal.valueOf(aClassMaximumPrice)) != 1,
                "Maximum price is greater than "+ aClassMaximumPrice);

        //write result to file
        writePricesToFile(maximumPrice,minimumPrice);
    }

}
