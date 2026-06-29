package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Day7_FileHandlingTest extends BaseTest {

    @Test
    public void testFileUpload() {
        driver.get("https://the-internet.herokuapp.com/upload");
        
        // TECHNIQUE 1: File Upload - Zero Flake
        // Step 1: Create a dummy file to upload
        String fileName = "test_resume.txt";
        Path filePath = Paths.get(System.getProperty("user.dir"), fileName);
        try {
            Files.writeString(filePath, "This is Niranjan's test resume");
        } catch (Exception e) { e.printStackTrace(); }
        
        // Step 2: sendKeys with absolute path - NO ROBOT CLASS
        WebElement uploadInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("file-upload")));
        uploadInput.sendKeys(filePath.toAbsolutePath().toString());
        
        driver.findElement(By.id("file-submit")).click();
        
        // Step 3: Verify upload success
        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h3")));
        Assert.assertEquals(successMsg.getText(), "File Uploaded!");
        System.out.println("File Upload: PASSED");
        
        // Cleanup
        try { Files.deleteIfExists(filePath); } catch (Exception e) {}
    }

    @Test
    public void testFileDownload() throws InterruptedException {
        // TECHNIQUE 2: Setup download directory in BaseTest - but demo here
        String downloadPath = System.getProperty("user.dir") + File.separator + "downloads";
        new File(downloadPath).mkdirs(); // Create folder if not exists
        
        // Note: For real project, set this in BaseTest ChromeOptions
        // Map<String, Object> prefs = new HashMap<>();
        // prefs.put("download.default_directory", downloadPath);
        // options.setExperimentalOption("prefs", prefs);
        
        driver.get("https://the-internet.herokuapp.com/download");
        
        WebElement fileLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("some-file.txt")));
        String fileName = fileLink.getText();
        fileLink.click();
        
        // TECHNIQUE 3: Zero Flake Wait for Download
        File downloadedFile = new File(downloadPath + File.separator + fileName);
        int attempts = 0;
        while (!downloadedFile.exists() && attempts < 20) {
            Thread.sleep(500); // Check every 0.5s for 10s max
            attempts++;
        }
        
        Assert.assertTrue(downloadedFile.exists(), "File download failed");
        Assert.assertTrue(downloadedFile.length() > 0, "Downloaded file is empty");
        System.out.println("File Download: PASSED - " + fileName);
        
        // Cleanup
        downloadedFile.delete();
    }
}
