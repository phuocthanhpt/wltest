package utils;

import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.Platform;


public class CustomExtentReports {

    public static ExtentReports createInstance(String fileName){
        Platform platform = Platform.getCurrent();
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);

        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(fileName);

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
        extentReports.setSystemInfo("Automation Tester", "DA QA auto");
        extentReports.setSystemInfo("Organization", "Doctor Anywhere");
        extentReports.setSystemInfo("Platform", platform.name());
        extentReports.setSystemInfo("Browser", BaseTest.browserInfo);

        return extentReports;
    }
}
