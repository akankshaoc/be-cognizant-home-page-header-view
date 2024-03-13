package utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverSetup {
	public static WebDriver driver;
	
	public static WebDriver getWebDriver() {
		driver = new EdgeDriver();
		driver.manage().window().setSize(new Dimension(1350, 670));
		return driver;		
	}
}
