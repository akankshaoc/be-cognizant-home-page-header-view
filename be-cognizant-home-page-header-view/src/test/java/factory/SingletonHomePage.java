package factory;

import page_objects.Home;

/**
 * This class provides access to a home page object, which once instantiated cannot be overwritten. Also, the same static home page object is returned every time demanded for. 
 * @author 2308990
 * @see page_objects.Home
 */
public class SingletonHomePage {
	// the main purpose of creating this class is to make sure that the same homepage is given to
	// all test_runner files
	private static Home homePage;

	public static Home getHomePage() {
		return homePage;
	}

	public static void setHomePage(Home homePage) {
		if(SingletonHomePage.homePage != null) return;
		SingletonHomePage.homePage = homePage;
	}
	
}
