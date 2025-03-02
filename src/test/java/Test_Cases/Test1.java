package Test_Cases;

import Base.BaseTest;
import org.testng.annotations.Test;

public class Test1 extends BaseTest {

    @Test
    public void testLogin() {
        driver.get("https://example.com");
        System.out.println("Website opened successfully.");
    }
}
