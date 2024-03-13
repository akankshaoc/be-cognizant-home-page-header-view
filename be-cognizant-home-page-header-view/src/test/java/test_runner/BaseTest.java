package test_runner;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import factory.SingletonDriver;
import factory.SingletonHomePage;
import page_objects.Home;
import utils.DriverSetup;

public interface BaseTest {
	@BeforeSuite
	default void init() {
		SingletonDriver.setDriver(DriverSetup.getWebDriver());
		SingletonHomePage.setHomePage(new Home(SingletonDriver.getDriver()));
	}

	@AfterSuite
	default void close() {
		SingletonDriver.closeDriver();
	}

}
