package test_cases;

import PageObjects.*;
import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.CustomListeners;

import java.util.Arrays;

public class WeatherMapTest extends BaseTest {

    private WebDriverWait wait;
    private static final Logger log = LogManager.getLogger();

    private LoginPage loginPage;
    private WeatherMapHomePage homePage;
    private APIKeysPage apiKeysPage;

    @BeforeClass
    @Parameters({"runMode", "browser"})
    public void setUp(String runMode, String browser) {
        log.info("Running 'beforeClass()' -> setUp()");
        try {
            // firstly init web driver and browser
            initWebDriverBrowser(runMode, browser);
            wait = new WebDriverWait(driver, 10);
            loginPage = new LoginPage(driver);
            homePage = new WeatherMapHomePage(driver);
            apiKeysPage = new APIKeysPage(driver);

        } catch (Exception e) {
            log.error("setUp() Error occurs: " + e.getMessage() + "\n StackTrace: " + Arrays.toString(e.getStackTrace()));
            CustomListeners.testReport.get().log(Status.FAIL, "setUp() Error occurs: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @AfterClass
    @Parameters({"clearData"})
    public void cleanUp(String clearData) throws InterruptedException {
        log.info("Running 'afterClass()' -> clean up test");
        try {
            if (clearData.equalsIgnoreCase("true")) {
                log.info("Clear data is true - do clean data");
                /*
                    Do clear data
                 */
            }
        } catch (Exception e) {
            log.info("Clean up Error occurs: " + e.getMessage());
            CustomListeners.testReport.get().log(Status.FAIL, "Clean up Error occurs: " + e.getMessage());
        } finally {
            // Close web driver
            if (driver != null) {
                log.info("Driver quit");
                driver.quit();
            }
        }
    }

    @BeforeMethod
    public void beforeMethod() {
        finalTestResult = true;
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (!finalTestResult) {
            result.setStatus(ITestResult.FAILURE);
        }
    }

    @Test
    @Parameters({"test_url", "username", "password", "newKey"})
    public void tc_CreateAPI(String url, String username, String password, String newKey) {
        try {
            log.info("Go to Login screen");
            loginPage.accessLoginPage(url);
            loginPage.doLogin(username, password);
            Thread.sleep(2000);
            homePage.goToAPIKeysScreen();
            Thread.sleep(1000);

            log.info("Create new key");
            apiKeysPage.generateNewKey(newKey);

            Thread.sleep(1000);

            // Verify new key has created
            String log = "Check new key '" + newKey + "' created success";
            CustomListeners.testReport.get().log(Status.INFO, log);
            WebElement newKeyElement = driver.findElement(By.xpath("//td[normalize-space()='"+ newKey +"']"));
            verifyEquals(true, newKeyElement.getText().equals(newKey));

        } catch (Exception e) {
            finalTestResult = false;
            e.printStackTrace();
            String error = "tc_loginSuccessfulWithValidUser - Error occurs: " + e.getMessage();
            log.error(error);
            CustomListeners.testReport.get().log(Status.FAIL, error);
        }
    }

    @Test
    @Parameters({"test_url", "username", "password", "newKey"})
    public void tc_DeleteKey(String url, String username, String password, String newKey) {
        try {
            log.info("Go to Login screen");
            loginPage.accessLoginPage(url);
            loginPage.doLogin(username, password);
            Thread.sleep(2000);
            homePage.goToAPIKeysScreen();
            Thread.sleep(1000);

            log.info("Create new key");
            apiKeysPage.generateNewKey(newKey);

            Thread.sleep(1000);

            // Verify new key has created
            String log = "Check new key '" + newKey + "' created success";
            CustomListeners.testReport.get().log(Status.INFO, log);
            WebElement newKeyElement = driver.findElement(By.xpath("//td[normalize-space()='"+ newKey +"']"));
            verifyEquals(true, newKeyElement.getText().equals(newKey));

        } catch (Exception e) {
            finalTestResult = false;
            e.printStackTrace();
            String error = "tc_loginSuccessfulWithValidUser - Error occurs: " + e.getMessage();
            log.error(error);
            CustomListeners.testReport.get().log(Status.FAIL, error);
        }
    }
}