package factory;

import org.openqa.selenium.WebDriver;

/**
 * This class provides access to a web driver, which once instantiated cannot be overwritten. Also, the same static driver is returned every time demanded for. 
 * @author 2308990
 *
 */
public class SingletonDriver {
	// the main purpose of creating this class is to make sure that all of our test files
	// in test_runner folder uses the same driver
	private static WebDriver driver;

	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		if(SingletonDriver.driver != null) return;
		SingletonDriver.driver = driver;
	}

	public static void closeDriver() {
		if(driver != null) driver.quit();		
	}
	
	
}
