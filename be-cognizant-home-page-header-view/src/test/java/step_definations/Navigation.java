package step_definations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonWriter;

import components.MenuItem;
import factory.SingletonDriver;
import factory.SingletonHomePage;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import page_objects.Home;
import utils.Link;
import utils.property_readers.UserProperty;

public class Navigation {

	static Home homePage;
	static WebDriver driver;
	static List<MenuItem> menuItems;

	@Before
	public void init() {
		driver = SingletonDriver.getDriver();
		homePage = SingletonHomePage.getHomePage();
	}

	@Given("user on becognizant home page")
	public void userOnBecognizantHomePage() throws InterruptedException {
		if (driver.getTitle().equals(homePage.title))
			return;
		homePage.launch();
	}

	@When("user clicks on user icon")
	public void userClicksOnUserIcon() {
		homePage.clickOnUserIcon();
	}

	// Method for checking whether user information is captured same as mentioned in property file 
	@Then("user info displayed")
	public void userInfoDisplayed() {
		Map<String, String> userInfo = homePage.getUserInfo();
		System.out.println(userInfo);
		Assert.assertEquals(userInfo.get("user"), UserProperty.getProperty("user"));
		Assert.assertEquals(userInfo.get("email"), UserProperty.getProperty("email"));
	}

	Map<String, Integer> statusCodeLinks;

	// Method for creating navigation_actual.json file while navigating through top-navs
	@Given("user records all links available on nav bar")
	public void recordAllLinks() throws InterruptedException, JsonIOException, IOException {
		if(menuItems != null) return;
		menuItems = homePage.getTopNavItems();
		statusCodeLinks = new HashMap<>();
		JsonWriter writer = new JsonWriter(new BufferedWriter(new FileWriter("./test-output/output_data/navigation_actual.json")));
		writer.setIndent("    ");
		
		writeToJsonFile(menuItems, writer);
	}

	// Helper method doing actual implementation for json file writing
	private static void writeToJsonFile(List<MenuItem> items, JsonWriter writer) throws IOException {		
		writer.beginArray();
		for(MenuItem item : items) {
			writer.beginObject();
			
			writer.name("text").value(item.text);
			writer.name("link").value(item.link);
			
			writer.name("subMenuItems");
			writeToJsonFile(item.subMenuItems , writer);
			
			writer.endObject();
		}
		writer.endArray();
		writer.flush();
	}
	
	// Method for validating results for actual vs expected top-nav results
	@Then("all links should be listes as writen in navigation json")
	public void all_links_should_be_listes_as_writen_in_navigation_json() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		// Expected Result
		JsonElement requiredItems = JsonParser.parseReader(new BufferedReader(new FileReader("./src/test/resources/test_data/navigation_expected.json")));
		
		// Actual Result
		JsonElement observedItems = JsonParser.parseReader(new BufferedReader(new FileReader("./test-output/output_data/navigation_actual.json")));
	    
		Assert.assertEquals(observedItems, requiredItems);
	}

	// Method for checking if all the links accessed through top nav-bar are working
	@When("user visits each of them")
	public void user_visits_each_of_them() {
		menuItems.stream().forEach(menuItem -> {
			try {
				int statusCode = Link.getHttpResponseCode(menuItem.link);
				statusCodeLinks.put(menuItem.link, statusCode);
			} catch (IOException e) {
				if (menuItem.link != null && !menuItem.link.equals("+buttonmenu")) {
					throw new AssertionError("deformed url : " + menuItem.link);
				}
			}
		});
	}

	@Then("all links should work")
	public void all_links_should_work() {
		List<String> brokenLinks = new ArrayList<>();
		for (String link : statusCodeLinks.keySet()) {
			if (statusCodeLinks.get(link) == 404 || statusCodeLinks.get(link) > 500) {
				brokenLinks.add(link);
			}
		}

		if (brokenLinks.size() > 0) {
			throw new AssertionError(brokenLinks.size() + " links from the top navigation are broken - " + brokenLinks);
		}
	}
}
