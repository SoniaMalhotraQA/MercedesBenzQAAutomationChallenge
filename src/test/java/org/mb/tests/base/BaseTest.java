package org.mb.tests.base;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.mb.factory.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class BaseTest {

    //Web driver
    protected WebDriver driver;
    
    
    @Parameters("browser")
    @BeforeMethod
    public void setup(@Optional final String browser){
        driver = DriverManager.initializeDriver(browser);
    }

    @Parameters("browser")
    @AfterMethod
    public void teardown(@Optional final String browser, ITestResult result) throws IOException {
        takeScreenShot(getFileForScreenshot(browser,result,false));
        //takeFullScreenShot(getFileForScreenshot(browser,result,true));
        driver.quit();
    }

    /**
     * Generates File for saving screenshot using browser name and test case data
     * @param browser
     * @param result
     * @param fullscreen
     * @return
     */
    private File getFileForScreenshot(String browser, final ITestResult result, final boolean fullscreen) {
        String subFolder = "success";
        if(result.getStatus() != 1)
            subFolder = "failure";

        String appendFullscreen = "";
        if(fullscreen)
            appendFullscreen = "_fullscreen";

        if(StringUtils.isEmpty(browser))
            browser = "chrome";

        File file = new File("results" + File.separator + "screenshots" +
                File.separator + subFolder  + File.separator + browser + File.separator +
                result.getTestClass().getRealClass().getSimpleName() + "_"+
                result.getMethod().getMethodName() + appendFullscreen + ".png");
        return file;
    }

    /**
     * Takes screenshot and saves it
     * @param destFile
     * @throws IOException
     */
    public void takeScreenShot(File destFile) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, destFile);
    }

    /**
     * Takes full screenshot using Ashot
     * @param destFile
     */
    private void takeFullScreenShot(File destFile){
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(driver);

        try{
            ImageIO.write(screenshot.getImage(), "PNG",destFile);
        } catch (IOException e) {
            throw new RuntimeException("Error while saving screenshot");
        }

    }

    /**
     * The method returns un-formatted price value by removing currency symbol
     * @param amount
     * @param locale
     * @return
     * @throws ParseException
     */
    public BigDecimal getUnformattedPrice(final String amount, final Locale locale) throws ParseException {
        final NumberFormat format = NumberFormat.getNumberInstance(locale);
        if (format instanceof DecimalFormat) {
            ((DecimalFormat) format).setParseBigDecimal(true);
        }
        return (BigDecimal) format.parse(amount.replaceAll("[^\\d.,]",""));
    }

    /**
     * The method writes highest vehicle price and lowest vehicle price in a text file
     * @param highestValue
     * @param lowestValue
     * @throws IOException
     */
    public void writePricesToFile(BigDecimal highestValue, BigDecimal lowestValue) throws IOException {

        File resultFile = new File("results" + File.separator + "resultFile.text");
        resultFile.createNewFile();
        Writer writer = new BufferedWriter(new FileWriter(resultFile));
        writer.write("Highest price of A-Class hatchback is : "+highestValue.toString());
        writer.write("\nLowest price of A-Class hatchback is : "+lowestValue.toString());
        writer.close();
    }
}
