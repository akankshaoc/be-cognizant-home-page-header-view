package step_definations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import factory.SingletonDriver;
import factory.SingletonHomePage;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import page_objects.Home;
import utils.Link;
import page_objects.EthicsAndCompliancePage;

public class EthicsAndCompliance {
	static Home homePage;
	static WebDriver driver;
	static EthicsAndCompliancePage enpPage;
	
	@Before
	public void init() {
		driver = SingletonDriver.getDriver();
		homePage = SingletonHomePage.getHomePage();
	}
	
	@Given("user on ethics and compliance page")
	public void user_on_ethics_and_compliance_page() throws InterruptedException {
		if(driver.getTitle().equals(enpPage.title)) return;
		user_navigates_to_ethics_and_compliance();
	}

	Map<String, Integer> resourceStatusCodes;
	@When("user tries openening all resources and links")
	public void user_tries_openening_all_resources_and_links() {
		resourceStatusCodes = new HashMap<>();
		Map<String, String> resourcesMap = enpPage.getResources();
		for(Entry<String, String> resouEntry : resourcesMap.entrySet()) {
			String resourceNameString = resouEntry.getValue();
			String linkString = resouEntry.getValue();
			
			int statusCode;
			try {
				statusCode = Link.getHttpResponseCode(linkString);
				resourceStatusCodes.put(resourceNameString, statusCode);
			} catch (IOException e) {
				throw new AssertionError("Malformed resource link : " + linkString);
			}
		}
	}

	@Then("all links and resources should be working")
	public void all_links_and_resources_should_be_working() {
		List<String> brokenLinks = new ArrayList<>();
		for(String resource : resourceStatusCodes.keySet()) {
			if(resourceStatusCodes.get(resource) == 404 || resourceStatusCodes.get(resource) > 500) {
				brokenLinks.add(resource);
			}
		}
		
		if(brokenLinks.size() > 0) {
			throw new AssertionError("Resources Not Found : " + brokenLinks);
		}
	}
	
	@When("user navigates to ethics and compliance")
	public void user_navigates_to_ethics_and_compliance() throws InterruptedException {
		enpPage = homePage.navigateToEtichsAndCompliance();
		Thread.sleep(2000);
	}

	@Then("user is redirected to ethics and compliance")
	public void user_is_redirected_to_ethics_and_compliance() {
	    Assert.assertEquals(driver.getTitle(), enpPage.title);
	}
	
	@When("user looks at focus area")
	public void user_looks_at_focus_area() {
	}

	@Then("user finds {string}")
	public void user_finds(String string) {
	    boolean hasFocusArea = enpPage.hasFocusAreas(string);
	    Assert.assertTrue(hasFocusArea, string + " focus are missing on ethics and compliance page");
	}
}
