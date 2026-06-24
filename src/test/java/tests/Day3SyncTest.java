package tests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;

import pages.AlertPage;
import pages.DropdownPage;
import utils.DriverSetup;

public class Day3SyncTest {
    WebDriver driver;
    
    @Test
    public void dropdownTest() {
        driver = DriverSetup.initDriver();
        driver.get("https://the-internet.herokuapp.com/dropdown");
        
        DropdownPage dropPage = new DropdownPage(driver);
        dropPage.selectOption("Option 2");
        
        String selected = dropPage.getSelectedOption();
        Assert.assertEquals(selected, "Option 2", "Dropdown selection failed");
    }
    
    @AfterMethod
    public void tearDown() {
        if(driver != null) {
            driver.quit();
        }
    }
@Test
public void jsAlertTest() {
    driver = DriverSetup.initDriver();
    driver.get("https://the-internet.herokuapp.com/javascript_alerts");
    
    AlertPage alertPage = new AlertPage(driver);
    alertPage.clickJSAlert();
    alertPage.acceptAlert();
    
    String result = alertPage.getResultText();
    Assert.assertEquals(result, "You successfully clicked an alert", "Alert handling failed");
}
}

