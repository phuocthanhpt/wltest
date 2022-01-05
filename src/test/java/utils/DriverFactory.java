package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class DriverFactory {
    private static final Logger log = LogManager.getLogger();
    private WebDriver driver;
    private String strOS = "";
    private String driverPath = "";

    public WebDriver getWebDriver() {
        initWebDriver();
        return this.driver;
    }

    /*
        Initial WebDriver based on the Operation system
     */
    private void initWebDriver() {
        log.info("Init web driver");
        try {
            strOS = System.getProperty("os.name").toLowerCase();
            driverPath += System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                    + File.separator + "resources" + File.separator + "executables" + File.separator;

            // If run test on Windows
            if (isWindows(strOS)) {
                driverPath += "windows" + File.separator;
            }

            // If run test on Mac
            if (isMac(strOS)) {
                driverPath += "mac" + File.separator;
            }

            System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
            log.info(driverPath);
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /*
        Check if current OS is Windows
     */
    private static boolean isWindows(String strOS) {
        return (strOS.contains("win"));
    }

    /*
        Check if current OS is Mac
     */
    private static boolean isMac(String strOS) {
        return (strOS.contains("mac"));
    }


    public void quitWebDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

}
