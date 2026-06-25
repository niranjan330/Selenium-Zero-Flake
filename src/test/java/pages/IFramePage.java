package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class IFramePage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    private By iframe = By.id("mce_0_ifr");
    private By editorBody = By.id("tinymce");
    
    public IFramePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void switchToFrame() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe));
    }
    
    public void typeInEditor(String text) {
        WebElement body = wait.until(ExpectedConditions.visibilityOfElementLocated(editorBody));
        
        // Zero Flake: Set text directly with JS. Works 100% for TinyMCE
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].innerHTML = arguments[1];", body, text);
    }
    
    public String getEditorText() {
        WebElement body = wait.until(ExpectedConditions.visibilityOfElementLocated(editorBody));
        return body.getText().trim();
    }
    
    public void exitFrame() {
        driver.switchTo().defaultContent(); // CRITICAL: Always exit
    }
}
