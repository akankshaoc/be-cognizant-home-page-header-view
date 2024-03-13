package test_runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src/test/resources/features/Navigation.feature" }, glue = { "step_definations" })
public class NavigationTests extends AbstractTestNGCucumberTests implements BaseTest {
}


//We are extending AbstractTestNGCucumberTests class so that we can execute test cases 
//using cucumber but internally the tests are being executed by testNG