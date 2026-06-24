package tests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.DriverSetup;

public class Day2LoginTest {
    WebDriver driver;
    
    @Test
    public void validLoginTest() {
        driver = DriverSetup.initDriver();
        driver.get("https://the-internet.herokuapp.com/login");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("tomsmith");
        loginPage.enterPassword("SuperSecretPassword!");
        loginPage.clickLogin();
        
        String actualMsg = loginPage.getWelcomeMessage();
        Assert.assertTrue(actualMsg.contains("You logged into a secure area!"));
    }
    
    @AfterMethod
    public void tearDown() {
        if(driver != null) {
            driver.quit(); // Browser closes AFTER test result is logged
        }
    }
}
