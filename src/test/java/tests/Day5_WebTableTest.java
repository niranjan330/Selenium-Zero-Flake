package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;

public class Day5_WebTableTest extends BaseTest {

    @Test
    public void verifyDynamicTableData() throws InterruptedException {
        driver.get("https://www.w3schools.com/html/html_tables.asp"); // STABLE SITE
        
        // Wait for table - W3Schools uses id="customers"
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customers")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", table);
        Thread.sleep(2000); 

        // Zero Flake: Get Company where Country = Germany
        String company = driver.findElement(By.xpath(
            "//td[text()='Germany']/preceding-sibling::td[2]"
        )).getText();
        
        System.out.println("Company in Germany: " + company);
        Assert.assertEquals(company, "Alfreds Futterkiste", "Data mismatch");
        
        // Zero Flake: Get Contact for Ernst Handel
        String contact = driver.findElement(By.xpath(
            "//td[text()='Ernst Handel']/following-sibling::td[1]"
        )).getText();
        Assert.assertEquals(contact, "Roland Mendel");
        
        System.out.println("Day 5 Test Passed. Zero Flake. W3Schools.");
    }
}