package utils;

import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.Arrays;

public class CustomListeners extends BaseTest implements ITestListener {
    private static Logger log = LogManager.getLogger();
    String methodName = "";

    @Override
    public void onTestStart(ITestResult iTestResult) {
        log.info("Test start");
        ExtentTest test = extentReport.createTest("@Test class: " + iTestResult.getTestClass().getName() + "</br>"
                + " @TestCase : " + iTestResult.getMethod().getMethodName());
        testReport.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        if(finalTestResult){
            log.info("Test success");
            String methodName = iTestResult.getMethod().getMethodName();
            String logText = "<b>" + "TEST CASE:- " + methodName.toUpperCase() + " PASSED" + "</b>";
            Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
            testReport.get().pass(m);
        }else{
            String failureLogg = "TEST CASE FAILED";
            Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
            testReport.get().log(Status.FAIL, m);
        }
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        log.error("Test failure");
        String exceptionMessage = Arrays.toString(iTestResult.getThrowable().getStackTrace());
        testReport.get().fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occurred: Click to see"
                + "</font>" + "</b >" + "</summary>" + exceptionMessage.replaceAll(",", "<br>") + "</details>" + " \n");

        try {
            TestUtil.captureScreenshot(iTestResult.getMethod().getMethodName());
            testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
                    MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.screenshotName).build());
            log.error("Screen shot has been captured: '" + TestUtil.screenshotPath + "'");
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        String failureLogg = "TEST CASE FAILED";
        Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
        testReport.get().log(Status.FAIL, m);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        log.info("Test skipped");
        String methodName = iTestResult.getMethod().getMethodName();
        String logText = "<b>" + "Test Case:- " + methodName + " Skipped" + "</b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
        testReport.get().skip(m);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        log.info("Test finish");
        if (extentReport != null) {
            extentReport.flush();
        }
    }
}
