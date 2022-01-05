package utils;

import base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtil extends BaseTest {
    public static String screenshotName;
    public static String screenshotPath;
    private static WebDriverWait wait = new WebDriverWait(driver, 30);

    /*
        Take screenshot
     */
    public static String captureScreenshot(String fileName) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("E_yyyyMMdd_hhmmss");
        Date date = new Date();
        screenshotName = fileName + "_" + dateFormat.format(date) + ".png";
//        String directory = System.getProperty("user.dir") + File.separator + "test_reports" + File.separator;
        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, new File(reportPath + screenshotName));
        FileUtils.copyFile(sourceFile, new File(System.getProperty("user.dir")
                + File.separator + "test_screenshots" + File.separator + screenshotName));
        screenshotPath = reportPath + screenshotName;

        return screenshotPath;
    }

}
