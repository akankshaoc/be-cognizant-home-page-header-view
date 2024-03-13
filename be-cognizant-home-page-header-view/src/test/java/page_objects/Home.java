package page_objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import components.MenuItem;

import java.time.Duration;

/**
 * Home Page Object
 * This page interacts with <a href = 'https://cognizantonline.sharepoint.com/sites/Be.Cognizant/SitePages/Home.aspx'> Be Cognizant Home Page</a>
 * @author 2308990
 *
 */
public class Home {
	// web driver
	private WebDriver driver;

	// page properties
	public String url = "https://cognizantonline.sharepoint.com/sites/Be.Cognizant/SitePages/Home.aspx";
	public String title = "Be.Cognizant - Home";

	// waits
	private Wait<WebDriver> twoSecondWait;
	private Wait<WebDriver> eightSecondWait;

	// casts
	Actions actions;

	// locators

	// 1. user account info locators
	private By userIconLocator = By.id("O365_MainLink_Me");
	private By userInformationContainerLocator = By.id("mectrl_main_body");
	private By userNameLocator = By.id("mectrl_currentAccount_primary");
	private By userEmailLocator = By.id("mectrl_currentAccount_secondary");

	// 2. main menu locators
	private By mainMenuItemLinksLocator = By.cssSelector(".ms-OverflowSet-item > a");
	private By mainMenuItemButtonsLocator = By.cssSelector(".ms-OverflowSet-item > button");
	private By subMenuItemLocator = By.cssSelector(".ms-ContextualMenu-item");
	private By subMenuCaretLocator = By.xpath("//i[@data-icon-name = 'CaretRight']");
	private By openSubMenuLocator = By.cssSelector(".ms-ContextualMenu-list.is-open");
	private By corporateFunctionsMenuLocator = By.cssSelector("button[name = 'Corporate Functions']");
	private By legalAndCorporateSubmenuLocator = By.cssSelector("a[name = 'Legal & Corporate Affairs']");
	private By ethicsAndComplianceLinikLocator = By.cssSelector("a[name = 'Ethics and Compliance']");
	private By settingButtonLocator = By.id("O365_MainLink_Settings");

	public Home(WebDriver driver) {
		// 1. instantiation of the driver and other interactors
		this.driver = driver;
		this.actions = new Actions(driver);

		// 2. instantiation of waits
		twoSecondWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		eightSecondWait = new WebDriverWait(driver, Duration.ofSeconds(8));
	}

	public void launch() {
		driver.get(url);
	}

	public void clickOnUserIcon() {
		eightSecondWait.until(ExpectedConditions.visibilityOfElementLocated(settingButtonLocator));
		//1. click on the user icon
		driver.findElement(userIconLocator).click();
		// 2. wait for the visibility of user information
		twoSecondWait.until(ExpectedConditions.visibilityOfElementLocated(userInformationContainerLocator));
	}

	public Map<String, String> getUserInfo() {
		// 1. Instantiate result map
		Map<String, String> res = new HashMap<>();

		// 2. Retrieve user information and store in map
		res.put("user", driver.findElement(userNameLocator).getText());
		res.put("email", driver.findElement(userEmailLocator).getText());

		// 3. return result
		return res;
	}

	/**
	 * helper method to recursively retrieve sub menus and keep on checking whether further sub-menus exist or not
	 * @param submenuItem
	 * @return
	 * @throws InterruptedException
	 */
	private MenuItem retriveSubMenuItem(WebElement submenuItem) throws InterruptedException {
		// list of all opened unordered lists on the web page
		List<WebElement> openedLists = driver.findElements(openSubMenuLocator);

		// 1. add link if it exists
		String link = null;
		try {
			link = submenuItem.findElement(By.tagName("a")).getAttribute("href");
		} catch (NoSuchElementException e) {
			// link remains null
		}
		MenuItem res = new MenuItem(link, submenuItem.getText());

		// 2. add sub menu items
		try {
			// 2.1 look for caret icon
			submenuItem.findElement(subMenuCaretLocator);

			// 2.2 move to sub menu item
			actions.moveToElement(submenuItem).build().perform();

			// 2.3 wait for a new sub menu list to appear
			Thread.sleep(500);

			// 2.4 identify the new list
			WebElement newList = null;
			for (WebElement openedList : driver.findElements(openSubMenuLocator)) {
				if (!openedLists.contains(openedList)) {
					newList = openedList;
					break;
				}
			}

			if (newList == null)
				return res;

			// 2.5 add the sub menu items from the new list

			for (WebElement item : newList.findElements(subMenuItemLocator)) {
				res.subMenuItems.add(retriveSubMenuItem(item));
			}

		} catch (NoSuchElementException e) {
			// sub menu item list remains empty
		}

		return res;
	}
	
	/**
	 * makes a list of all top nav items and returns it
	 * @return
	 * @throws InterruptedException
	 */
	public List<MenuItem> getTopNavItems() throws InterruptedException {
		List<MenuItem> res = new ArrayList<>();

		List<WebElement> mainMenuLinks = driver.findElements(mainMenuItemLinksLocator);
		List<WebElement> mainMenuButtons = driver.findElements(mainMenuItemButtonsLocator);

		for (WebElement mainMenuLink : mainMenuLinks) {
			res.add(new MenuItem(mainMenuLink.getAttribute("href"), mainMenuLink.getText()));
		}

		for (WebElement mainMenuButton : mainMenuButtons) {
			MenuItem menuItem = new MenuItem("+buttonmenu", mainMenuButton.getText());
			mainMenuButton.click();
			twoSecondWait.until(ExpectedConditions.visibilityOfElementLocated(subMenuItemLocator));
			List<WebElement> subMenuItems = driver.findElements(subMenuItemLocator);
			for (WebElement subMenuItem : subMenuItems) {
				menuItem.subMenuItems.add(retriveSubMenuItem(subMenuItem));
			}

			res.add(menuItem);
		}

		return res;
	}
	
	/**
	 * navigates to Corporate Functions -> Legal & Corporate Affairs -> Ethics & Compliance
	 * @return
	 * @throws InterruptedException
	 */
	public EthicsAndCompliancePage navigateToEtichsAndCompliance() throws InterruptedException {
		WebElement corporateFunctionsMenu = driver.findElement(corporateFunctionsMenuLocator);
		corporateFunctionsMenu.click();
		Thread.sleep(500);

		WebElement legalAndCorporateSubMenu = driver.findElement(legalAndCorporateSubmenuLocator);
		actions.moveToElement(legalAndCorporateSubMenu).perform();
		Thread.sleep(500);

		WebElement ethicsAndComplianceLink = driver.findElement(ethicsAndComplianceLinikLocator);
		ethicsAndComplianceLink.click();
		eightSecondWait.until((d) -> {
			return d.getTitle() != title;
		});

		return new EthicsAndCompliancePage(driver);
	}
	
	
	public static void main(String args[]) throws InterruptedException {
		WebDriver driver = new EdgeDriver();
		driver.manage().window().setSize(new Dimension(2000, 670));
		Home page = new Home(driver);
		page.launch();
		page.clickOnUserIcon();
		System.out.println(page.getUserInfo());
		// System.out.println(page.navigateToEtichsAndCompliance().getResources());
		driver.quit();
	}
	

}
