package org.mb.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.mb.constants.BrowserType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {

    public static WebDriver initializeDriver(final String browser){
        WebDriver driver;
        String browserName = System.getProperty("browser",browser);
        if(StringUtils.isEmpty(browserName))
            browserName = "chrome";
        switch (BrowserType.valueOf(browserName.toUpperCase())) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
                break;
            case FIREFOX:
                // FireFox does not support Find Element(s) From Shadow Root
                // ,will be supported in v113 which is not yet released,
                //refer https://bugzilla.mozilla.org/show_bug.cgi?id=1700097
                throw new UnsupportedOperationException(
                        "FireFox does not support Find Element(s) From Shadow Root" +
                                ",\nrefer https://bugzilla.mozilla.org/show_bug.cgi?id=1700097");

                /*
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setAcceptInsecureCerts(true);
                driver = new FirefoxDriver(firefoxOptions)
                break;
                */
            case EDGE:
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--remote-allow-origins=*");
                driver = new EdgeDriver(edgeOptions);
                break;
            default:
                throw new IllegalArgumentException("Invalid Browser name: "+browserName);
        }
        if(driver != null)
            driver.manage().window().maximize();
        return driver;
    }
}
