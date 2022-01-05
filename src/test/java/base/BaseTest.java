package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import utils.CustomExtentReports;
import utils.CustomListeners;
import utils.TestUtil;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static String OS = "";
    public static WebDriver driver;
    public static String browserInfo;
    private static final Logger log = LogManager.getLogger();
    public static String reportName;
    public static ExtentReports extentReport;
    public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();
    public static String reportPath = System.getProperty("user.dir") + File.separator + "test_reports" + File.separator;
    public static String defaultDownloadPath = System.getProperty("user.dir") + File.separator + "test_reports" + File.separator + "downloadFiles";
    public static boolean finalTestResult = true;

    @BeforeSuite
    public void beforeSuite() {
        log.info("Running 'beforeSuite()'");

        // Get OS info
        OS = System.getProperty("os.name").toLowerCase();
        log.info("OS name: " + OS);
    }

    @AfterSuite
    public void afterSuite() {
        log.info("Running 'afterSuite()'");
    }

    @BeforeTest
    @Parameters({"browser"})
    public void beforeTest(String browser) throws IOException {
        log.info("Run 'beforeTest()'");
        String testClassName = this.getClass().getName();
        log.info("-------Test method: <" + testClassName + ">------------------------------------------------");

        DateFormat dateFormat = new SimpleDateFormat("E_yyyyMMdd_hhmmss");
        Date date = new Date();

        // Write into report
        browserInfo = browser;

        reportName = "TestReport_" + testClassName + "_" + dateFormat.format(date) + ".html";

        extentReport = CustomExtentReports.createInstance(reportPath + reportName);
        log.info("Init report: <" + reportName + ">");

    }

    @AfterTest
    public void afterTest() {
        log.info("Run 'afterTest()' method");
        log.info("Report path: <" + reportPath + ">");
    }

    /*
       Check if current OS is Windows
    */
    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    /*
        Check if current OS is Mac
     */
    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    public void initWebDriverBrowser(String runMode, String browserType) throws IOException {
        if (runMode.equalsIgnoreCase("local")) {
            log.info("Run on local machine - WebDriver");
            // get location of browser drivers
            String driverPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                    + File.separator + "resources" + File.separator + "executables" + File.separator;

            if (isWindows()) {
                driverPath += "windows" + File.separator;
                if (browserType.equalsIgnoreCase("chrome")) {
                    log.info("Set property for Chrome driver");
                    System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");

                    // Create Chrome driver
                    log.info("Create Chrome driver");

                    Map<String, Object> prefs = new HashMap<String, Object>();
                    prefs.put("download.default_directory", defaultDownloadPath); // for checking download
                    // Adding capabilities to ChromeOptions
                    ChromeOptions options = new ChromeOptions();
                    options.setExperimentalOption("prefs", prefs);

                    driver = new ChromeDriver(options);
                } else if (browserType.equalsIgnoreCase("firefox")) {
                    log.info("Set property for Firefox driver");
                    System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver.exe");
                    // Create Firefox driver
                    log.info("Create Firefox driver");
                    driver = new FirefoxDriver();
                } else if (browserType.equalsIgnoreCase("edge")) {
                    // Set property for Edge driver
                    log.info("Set property for Edge driver");
                    System.setProperty("webdriver.edge.driver", driverPath + "msedgedriver.exe");
                    // Create Edge driver
                    log.info("Create Edge driver");
                    driver = new EdgeDriver();
                } else {
                    log.error("Your browser is not supported");
                    CustomListeners.testReport.get().log(Status.FAIL, "Your browser is not supported");
                }
            } else if (isMac()) {
                driverPath += "mac" + File.separator;
                if (browserType.equalsIgnoreCase("chrome")) {
                    log.info("Set property for Chrome driver");
                    System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver");
                    // Create Chrome driver
                    log.info("Create Chrome driver");
                    driver = new ChromeDriver();
                } else if (browserType.equalsIgnoreCase("firefox")) {
                    log.info("Set property for Firefox driver");
                    System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver");
                    // Create Firefox driver
                    log.info("Create Firefox driver");
                    driver = new FirefoxDriver();
                } else {
                    log.error("Your browser is not supported");
                    CustomListeners.testReport.get().log(Status.FAIL, "Your browser is not supported");
                }
            } else {
                log.error("Your OS is not supported");
                CustomListeners.testReport.get().log(Status.FAIL, "Your OS is not supported");
            }

            log.info("'" + browserType + "' path: <" + driverPath + ">");
        }

        if (driver != null) {
            String title = driver.getTitle();
            log.info("Webdriver is created");
        } else {
            log.info("Webdriver is NULL");
        }

        // Maximize browser
        log.info("Maximize browser");
        driver.manage().window().maximize();

        // Define an implicit wait for loading web elements
        driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
    }

    /*
        Soft assure to verify 2 boolean values
        Take screenshot if not equal
        Write step to report
     */
    public static void verifyEquals(boolean bol_expected, boolean bol_actual) throws IOException {
        try {
            log.info("Verify number of searched result");
            log.info("--Actual: " + bol_actual);
            log.info("--Expected: " + bol_expected);
            Assert.assertEquals(bol_actual, bol_expected);
        } catch (Throwable t) {
            TestUtil.captureScreenshot("verifiedResult");
            // ReportNG
            Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
            Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
                    + " height=200 width=200></img></a>");
            Reporter.log("<br>");
            Reporter.log("<br>");
            // Extent Reports
            CustomListeners.testReport.get().log(Status.FAIL, " Verification failed with exception : " + t.getMessage());
            CustomListeners.testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
                    MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.screenshotName)
                            .build());

            finalTestResult = false;
        }
    }

}
