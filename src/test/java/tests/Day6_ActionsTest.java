package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Day6_ActionsTest extends BaseTest {

    @Test
    public void testMouseHover() {
        driver.get("https://the-internet.herokuapp.com/hovers");
        
        WebElement img1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//img[@alt='User Avatar'])[1]")));
        
        // TECHNIQUE 1: Mouse Hover - Zero Flake
        Actions actions = new Actions(driver);
        actions.moveToElement(img1).perform();
        
        WebElement caption = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h5[text()='name: user1']")));
        Assert.assertTrue(caption.isDisplayed(), "Hover failed");
        System.out.println("Mouse Hover: PASSED");
    }

    @Test
    public void testDragAndDrop() {
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");
        
        WebElement source = wait.until(ExpectedConditions.elementToBeClickable(By.id("column-a")));
        WebElement target = driver.findElement(By.id("column-b"));
        
        // TECHNIQUE 2: Drag & Drop - Zero Flake
        new Actions(driver).dragAndDrop(source, target).perform();
        
        String header = driver.findElement(By.xpath("//div[@id='column-a']/header")).getText();
        Assert.assertEquals(header, "B", "DragDrop failed");
        System.out.println("Drag & Drop: PASSED");
    }

    @Test
    public void testRightClickAndDoubleClick() {
        driver.get("https://the-internet.herokuapp.com/context_menu");
        
        WebElement box = wait.until(ExpectedConditions.elementToBeClickable(By.id("hot-spot")));
        
        // TECHNIQUE 3: Right Click - Zero Flake
        new Actions(driver).contextClick(box).perform();
        driver.switchTo().alert().accept();
        
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        WebElement checkbox1 = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("(//input[@type='checkbox'])[1]")));
        
        // TECHNIQUE 4: Double Click + JS Click fallback
        new Actions(driver).doubleClick(checkbox1).perform(); // Checks then unchecks
        
        System.out.println("Right/Double Click: PASSED");
    }

    @Test
    public void testJSExecutorScrollAndClick() {
        driver.get("https://the-internet.herokuapp.com/large");
        
        WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("large-table")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", table);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        
        String mainWindow = driver.getWindowHandle(); // SAVE current window
        
        WebElement footerLink = driver.findElement(By.linkText("Elemental Selenium"));
        js.executeScript("arguments[0].click();", footerLink);
        
        // FIX: Switch to new tab before assert
        wait.until(ExpectedConditions.numberOfWindowsToBe(2)); // Wait for new tab
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(mainWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        Assert.assertTrue(driver.getCurrentUrl().contains("elementalselenium"), "JS Click failed");
        System.out.println("JS Executor: PASSED");
        
        driver.close(); // Close new tab
        driver.switchTo().window(mainWindow); // Back to main
    }
}