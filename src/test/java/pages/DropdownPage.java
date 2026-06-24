package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.DriverSetup;

import java.time.Duration;

public class DropdownPage {
    WebDriver driver;
    WebDriverWait wait;
    
    // Locator
    private By dropdown = By.id("dropdown");
    
    public DropdownPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    // Action: Select by visible text = Zero Flake
    public void selectOption(String optionText) {
        WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdown));
        DriverSetup.debugSleep();
        Select select = new Select(dropdownElement);
        select.selectByVisibleText(optionText);
        DriverSetup.debugSleep();
    }
    
    public String getSelectedOption() {
        WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdown));
        Select select = new Select(dropdownElement);
        return select.getFirstSelectedOption().getText();
    }
}