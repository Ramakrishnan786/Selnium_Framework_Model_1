package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import Utils.ExtentManager;
import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    public WebDriver driver;
    protected ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        ExtentManager.getInstance();
    }

    @BeforeMethod
    public void setup(ITestResult result) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        test = ExtentManager.startTest(result.getMethod().getMethodName());
        test.info("Browser Launched");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("Test Failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed");
        }
        
        if (driver != null) {
            driver.quit();
            test.info("Browser Closed");
        }
    }

    @AfterSuite
    public void tearDownReport() {
        ExtentManager.flush();
    }
}
