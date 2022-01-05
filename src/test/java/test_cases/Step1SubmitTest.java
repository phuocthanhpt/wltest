package test_cases;

import PageObjects.Step1Page;
import PageObjects.Step2Page;
import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.CustomListeners;

import java.util.Arrays;

public class Step1SubmitTest extends BaseTest {

    private WebDriverWait wait;
    private static final Logger log = LogManager.getLogger();

    private Step1Page step1Page;
    private Step2Page step2Page;

    @BeforeClass
    @Parameters({"runMode", "browser"})
    public void setUp(String runMode, String browser) {
        log.info("Running 'beforeClass()' -> setUp()");
        try {
            // firstly init web driver and browser
            initWebDriverBrowser(runMode, browser);
            wait = new WebDriverWait(driver, 10);
            step1Page = new Step1Page(driver);
            step2Page = new Step2Page(driver);

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
    @Parameters({"test_url"})
    public void tc_emailValidationTest(String url){
        try {
            log.info("Test Step 1 screen: invalid email check");

            step1Page.accessSignUpPersonalPage(url);
            step1Page.enterEmail("sadfasdf");
            step1Page.clickStep1NextButton();

            // Verify Step1 submit success, Step 2 screen appear
            CustomListeners.testReport.get().log(Status.INFO, "Test Step 1 screen: invalid email check");
            verifyEquals(true, step1Page.isEmailInvalid());

            Thread.sleep(5000);

        } catch (Exception e) {
            finalTestResult = false;
            e.printStackTrace();
            String error = "tc_loginSuccessfulWithValidUser - Error occurs: " + e.getMessage();
            log.error(error);
            CustomListeners.testReport.get().log(Status.FAIL, error);
        }
    }

    @Test
    @Parameters({"test_url", "firstName", "lastName", "email", "month", "day", "year"})
    public void tc_step1SubmitTest(String url, String firstName, String lastName, String email,
                                   String month, String day, String year) {
        try {
            log.info("Test Step 1 screen submit success");

            step1Page.accessSignUpPersonalPage(url);
            step1Page.fillStep1Values(firstName, lastName, email, month, day, year);

            // Verify Step1 submit success, Step 2 screen appear
            CustomListeners.testReport.get().log(Status.INFO, "Test Step 1 screen submit success");
            verifyEquals(true, step2Page.getStep2Title().equals("Add your address"));

            Thread.sleep(5000);

        } catch (Exception e) {
            finalTestResult = false;
            e.printStackTrace();
            String error = "tc_loginSuccessfulWithValidUser - Error occurs: " + e.getMessage();
            log.error(error);
            CustomListeners.testReport.get().log(Status.FAIL, error);
        }

    }
}
