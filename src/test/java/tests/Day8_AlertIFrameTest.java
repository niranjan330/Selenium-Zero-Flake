package tests;

import base.BaseTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Day8_AlertIFrameTest extends BaseTest {

    @Test
    public void testJavaScriptAlerts() {
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        
        // TECHNIQUE 1: Simple Alert - Accept
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(), "I am a JS Alert");
        alert.accept(); // Click OK
        String result1 = driver.findElement(By.id("result")).getText();
        Assert.assertEquals(result1, "You successfully clicked an alert");
        System.out.println("JS Alert: PASSED");
        
        // TECHNIQUE 2: Confirm Alert - Dismiss
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(), "I am a JS Confirm");
        alert.dismiss(); // Click Cancel
        String result2 = driver.findElement(By.id("result")).getText();
        Assert.assertEquals(result2, "You clicked: Cancel");
        System.out.println("JS Confirm: PASSED");
        
        // TECHNIQUE 3: Prompt Alert - SendKeys
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(), "I am a JS prompt");
        alert.sendKeys("Niranjan SDET");
        alert.accept(); // Click OK
        String result3 = driver.findElement(By.id("result")).getText();
        Assert.assertEquals(result3, "You entered: Niranjan SDET");
        System.out.println("JS Prompt: PASSED");
    }

    @Test
    public void testNestedIFrames() {
        driver.get("https://the-internet.herokuapp.com/nested_frames");
        
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("frame-top")));
        driver.switchTo().frame("frame-left");
        
        WebElement leftBody = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        Assert.assertEquals(leftBody.getText().trim(), "LEFT");
        System.out.println("Nested iFrame LEFT: PASSED");
        
        driver.switchTo().parentFrame();
        driver.switchTo().frame("frame-middle");
        
        WebElement middleBody = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content")));
        Assert.assertEquals(middleBody.getText().trim(), "MIDDLE");
        System.out.println("Nested iFrame MIDDLE: PASSED");
        
        driver.switchTo().defaultContent();
        // FINAL FIX - Check frame-top exists in main DOM
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("frame-top")));
        Assert.assertTrue(driver.findElements(By.name("frame-top")).size() > 0, "Not back to main frame context");
        System.out.println("iFrame Exit: PASSED");
    }
}
