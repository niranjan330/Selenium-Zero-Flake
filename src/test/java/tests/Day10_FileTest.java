package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day10_FileTest extends BaseTest {

    @Test
    public void testFileUpload() {
        driver.get("https://the-internet.herokuapp.com/upload");
        
        // TECHNIQUE 1: Upload without clicking - Zero Flake
        // Find <input type="file"> and sendKeys with absolute path
        WebElement fileInput = driver.findElement(By.id("file-upload"));
        
        // Create dummy file for CI/CD - no local dependency
        String filePath = System.getProperty("user.dir") + File.separator + "testUpload.txt";
        try {
            Files.write(Paths.get(filePath), "Sparky Day 10 Zero Flake".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        fileInput.sendKeys(filePath); // NEVER click upload button first
        driver.findElement(By.id("file-submit")).click();
        
        WebElement uploaded = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uploaded-files")));
        Assert.assertEquals(uploaded.getText(), "testUpload.txt");
        System.out.println("File Upload: PASSED");
        
        // Cleanup
        new File(filePath).delete();
    }

    @Test
    public void testFileDownload() {
        driver.get("https://the-internet.herokuapp.com/download");
        
        // TECHNIQUE 2: Verify file exists before download click
        WebElement downloadLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("some-file.txt")));
        
        String href = downloadLink.getAttribute("href");
        Assert.assertTrue(href.contains("some-file.txt"));
        System.out.println("Download link valid: PASSED. URL = " + href);
        
        // NOTE: Actual download needs Chrome prefs. For CI, verify link 200 OK instead
        try {
            HttpURLConnection http = (HttpURLConnection) new URL(href).openConnection();
            http.setRequestMethod("HEAD");
            int responseCode = http.getResponseCode();
            Assert.assertEquals(responseCode, 200);
            System.out.println("Download URL 200 OK: PASSED");
        } catch (IOException e) {
            Assert.fail("Download URL failed: " + e.getMessage());
        }
    }

    @Test
    public void testBrokenImages() {
        driver.get("https://the-internet.herokuapp.com/broken_images");
        
        // TECHNIQUE 3: Check all images via HTTP status - Zero Flake
        List<WebElement> images = driver.findElements(By.tagName("img"));
        int brokenCount = 0;
        
        for (WebElement img : images) {
            String src = img.getAttribute("src");
            if (src == null || src.isEmpty()) continue;
            
            try {
                HttpURLConnection http = (HttpURLConnection) new URL(src).openConnection();
                http.setRequestMethod("HEAD");
                http.setConnectTimeout(3000);
                http.connect();
                
                if (http.getResponseCode() != 200) {
                    System.out.println("BROKEN: " + src + " Status: " + http.getResponseCode());
                    brokenCount++;
                } else {
                    System.out.println("OK: " + src);
                }
            } catch (IOException e) {
                System.out.println("ERROR: " + src + " " + e.getMessage());
                brokenCount++;
            }
        }
        
        System.out.println("Total Broken Images: " + brokenCount);
        Assert.assertTrue(brokenCount > 0, "Test site should have broken images");
    }
}
