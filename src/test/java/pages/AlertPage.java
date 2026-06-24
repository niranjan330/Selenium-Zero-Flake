package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AlertPage {
    WebDriver driver;
    WebDriverWait wait;
    
    private By jsAlertBtn = By.xpath("//button[text()='Click for JS Alert']");
    private By resultText = By.id("result");
    
    public AlertPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void clickJSAlert() {
        wait.until(ExpectedConditions.elementToBeClickable(jsAlertBtn)).click();
    }
    
    public void acceptAlert() {
        // Wait for alert to be present, then accept
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }
    
    public String getResultText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(resultText)).getText();
    }
}
