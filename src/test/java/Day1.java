import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;

public class Day1 {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.google.com");
        
        driver.findElement(By.name("q")).sendKeys("Manual to Automation in 20 days");
        driver.findElement(By.name("q")).submit();
        
        System.out.println("Page Title: " + driver.getTitle());
        Thread.sleep(2000);
        driver.quit();
        System.out.println("Day 1 Script: PASS");
    }
}
