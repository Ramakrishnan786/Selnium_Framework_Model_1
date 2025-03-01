package Utils;

import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;
import Base.BaseTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class TestListener implements ITestListener {

    public void onTestStart(ITestResult result) {
        ExtentManager.startTest(result.getMethod().getMethodName());
        Allure.step("Starting test: " + result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        ExtentManager.getTest().log(Status.PASS, "Test Passed");
        Allure.step("Test Passed: " + result.getMethod().getMethodName());
    }

    public void onTestFailure(ITestResult result) {
        WebDriver driver = ((BaseTest) result.getInstance()).driver;
        String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName());

        // Attach screenshot to Extent Report
        try {
            ExtentManager.getTest().fail("Test Failed: " + result.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            ExtentManager.getTest().fail("Test Failed, but screenshot could not be attached.");
        }

        // Capture and attach screenshot to Allure Report
        AllureUtils.captureScreenshot(driver);

        // Attach error message to Allure Report
        String errorMessage = "Test Failed: " + result.getThrowable().getMessage();
        Allure.addAttachment("Failure Details", new ByteArrayInputStream(errorMessage.getBytes(StandardCharsets.UTF_8)));
    }

    public void onTestSkipped(ITestResult result) {
        ExtentManager.getTest().log(Status.SKIP, "Test Skipped");
        Allure.step("Test Skipped: " + result.getMethod().getMethodName());
    }

    public void onFinish(ITestContext context) {
        ExtentManager.flush();
        Allure.step("Test Suite Execution Completed");
    }
}
