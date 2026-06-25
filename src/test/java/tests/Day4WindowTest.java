package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.IFramePage;
import pages.WindowAssassinPage;

public class Day4WindowTest extends BaseTest {
    
    @Test(priority = 1)
    public void testNewWindowSwitch() {
        driver.get("https://the-internet.herokuapp.com/windows");
        
        String mainWindow = driver.getWindowHandle();
        WindowAssassinPage windowPage = new WindowAssassinPage(driver);
        
        windowPage.openNewWindow();
        String newWindowText = windowPage.switchToNewWindowAndGetText(mainWindow);
        
        Assert.assertEquals(newWindowText, "New Window", "New window text mismatch");
        
        windowPage.closeCurrentWindowAndSwitchBack(mainWindow);
        Assert.assertEquals(driver.getTitle(), "The Internet", "Failed to return to main window");
    }
    
    @Test(priority = 2)
    public void testIFrameTyping() {
        driver.get("https://the-internet.herokuapp.com/iframe");
        
        IFramePage iframePage = new IFramePage(driver);
        String testText = "Zero Flake Assassin";
        
        iframePage.switchToFrame();
        iframePage.typeInEditor(testText);
        
        String actualText = iframePage.getEditorText();
        Assert.assertEquals(actualText, testText, "IFrame text mismatch");
        
        iframePage.exitFrame(); // Zero Flake: Always cleanup
    }
}