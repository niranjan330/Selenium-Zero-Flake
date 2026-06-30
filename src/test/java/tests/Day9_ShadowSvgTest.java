package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Day9_ShadowSvgTest extends BaseTest {

	@Test
	public void testShadowDOM() {
	    driver.get("https://the-internet.herokuapp.com/shadowdom");

	    WebElement shadowHost = wait.until(ExpectedConditions.presenceOfElementLocated(
	        By.cssSelector("my-paragraph")));

	    // TECHNIQUE 1: Get rendered text from host - proves shadow DOM works
	    String renderedText = shadowHost.getText().trim();
	    Assert.assertEquals(renderedText, "Let's have some different text!");
	    System.out.println("Shadow DOM Host Text: PASSED. Rendered = " + renderedText);

	    // TECHNIQUE 2: Prove you can enter shadow root - for interviews
	    SearchContext shadowRoot = shadowHost.getShadowRoot();
	    WebElement slotElement = shadowRoot.findElement(By.cssSelector("slot[name='my-text']"));
	    Assert.assertTrue(slotElement.isDisplayed()); // Slot exists in shadow
	    System.out.println("Shadow DOM getShadowRoot: PASSED");

	    // TECHNIQUE 3: JS Executor - access shadow internals
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    String fallbackText = (String) js.executeScript(
	        "return arguments[0].shadowRoot.querySelector('slot').textContent", shadowHost);
	    Assert.assertEquals(fallbackText.trim(), "My default text");
	    System.out.println("Shadow DOM JS Fallback: PASSED. Fallback = " + fallbackText);
	}

    @Test
    public void testSVGElements() {
        String svgPage = "data:text/html,<html><body><svg width='100' height='100'><circle cx='50' cy='50' r='40' fill='blue' id='testCircle'/><rect width='30' height='30' fill='orange' id='testRect'/></svg></body></html>";
        driver.get(svgPage);

        WebElement svgCircle = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[name()='svg']/*[name()='circle' and @id='testCircle']")));
        Assert.assertEquals(svgCircle.getAttribute("fill"), "blue");
        System.out.println("SVG XPath Circle: PASSED");

        WebElement svgRect = driver.findElement(By.cssSelector("svg rect#testRect"));
        Assert.assertEquals(svgRect.getAttribute("fill"), "orange");
        System.out.println("SVG CSS Rect: PASSED");
    }
}