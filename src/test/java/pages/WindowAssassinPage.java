package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Set;

public class WindowAssassinPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    private By clickHereLink = By.linkText("Click Here");
    private By newWindowText = By.tagName("h3");
    
    public WindowAssassinPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void openNewWindow() {
        wait.until(ExpectedConditions.elementToBeClickable(clickHereLink)).click();
    }
    
    public String switchToNewWindowAndGetText(String mainWindowHandle) {
        wait.until(ExpectedConditions.numberOfWindowsToBe(2)); // Zero Flake: wait for new tab
        
        Set<String> allWindows = driver.getWindowHandles();
        for(String window : allWindows) {
            if(!window.equals(mainWindowHandle)) {
                driver.switchTo().window(window);
                break;
            }
        }
        return wait.until(ExpectedConditions.visibilityOfElementLocated(newWindowText)).getText();
    }
    
    public void closeCurrentWindowAndSwitchBack(String mainWindowHandle) {
        driver.close(); // close current tab
        driver.switchTo().window(mainWindowHandle); // back to main
    }
}