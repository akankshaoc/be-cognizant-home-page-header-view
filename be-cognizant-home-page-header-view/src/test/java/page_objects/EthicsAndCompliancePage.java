package page_objects;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class EthicsAndCompliancePage {
	private WebDriver driver;
	private Actions actions;

	private By resourceLocator = By
			.xpath("//div[@id='vpc_WebPart.QuickLinksWebPart.internal.2cba7373-faf4-49c9-9139-551a6d0e3281'] //a");
	public String title = "Ethics & Compliance";

	EthicsAndCompliancePage(WebDriver driver) {
		this.driver = driver;
		actions = new Actions(driver);
	}
	
	/**
	 * check if a focus area exists with the inner text of the given parameter
	 * @param focusArea
	 * @return
	 */
	public boolean hasFocusAreas(String focusArea) {
		try {
			driver.findElement(By.xpath("//*[contains(text(), '"+focusArea+"')]"));
			return true;
		} catch(NoSuchElementException e) {
			// focus area does not exist
			return false;
		}
	}
	
	/**
	 * locates all resources listed on the compliance page and return a map
	 * @return Map <title of resource, link of the resource>
	 */
	public Map<String, String> getResources() {
		Map<String, String> titleLinks = new HashMap<>();
		driver.findElements(resourceLocator).stream().forEach(anchor -> {
			actions.moveToElement(anchor);
			titleLinks.put(anchor.getAttribute("aria-label").trim(), anchor.getAttribute("href"));
		});
		return titleLinks;
	}
}
