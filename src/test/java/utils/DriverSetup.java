package utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class DriverSetup {
    public static WebDriver initDriver() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }
    public static void debugSleep() {
        try {
            Thread.sleep(5000); // 1 second pause. Change to 2000 for slower
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}