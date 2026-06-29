package utils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HighlightUtil {
    public static void flash(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.background='yellow'", element);
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        js.executeScript("arguments[0].style.background=''", element);
    }
}