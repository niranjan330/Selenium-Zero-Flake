package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;
    
    // Locators
    private By username = By.id("username");
    private By password = By.id("password");
    private By loginBtn = By.cssSelector("button[type='submit']");
    private By welcomeText = By.cssSelector(".flash.success");
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    // Actions with waits = ZERO FLAKE
    public void enterUsername(String user) { 
        wait.until(ExpectedConditions.visibilityOfElementLocated(username)).sendKeys(user); 
    }
    public void enterPassword(String pass) { 
        wait.until(ExpectedConditions.visibilityOfElementLocated(password)).sendKeys(pass); 
    }
    public void clickLogin() { 
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click(); 
    }
    public String getWelcomeMessage() { 
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeText)).getText(); 
    }
}
