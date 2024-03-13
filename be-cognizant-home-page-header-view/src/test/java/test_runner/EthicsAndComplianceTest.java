package test_runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "./src/test/resources/features/EthicsAndCompliance.feature" }, glue = {
		"step_definations" })
public class EthicsAndComplianceTest extends AbstractTestNGCucumberTests implements BaseTest {

}

// We are extending AbstractTestNGCucumberTests class so that we can execute test cases 
// using cucumber but internally the tests are being executed by testNG

// We are implementing BaseTest custom class to make sure our 
// BeforeSuite and AfterSuite methods are implementing properly