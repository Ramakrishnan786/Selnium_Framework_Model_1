package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = new ExtentReports();
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
            sparkReporter.config().setReportName("Selenium Automation Report");
            sparkReporter.config().setDocumentTitle("Test Execution Report");
            extent.attachReporter(sparkReporter);
        }
        return extent;
    }

    public static ExtentTest startTest(String testName) {
        ExtentTest test = getInstance().createTest(testName);
        extentTest.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void flush() {
        getInstance().flush();
    }
}
