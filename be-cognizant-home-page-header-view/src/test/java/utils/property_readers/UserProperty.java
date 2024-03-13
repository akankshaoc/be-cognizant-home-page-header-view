package utils.property_readers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UserProperty {

	public static final String filePath = "./src/test/resources/configs/user.properties";
	
	private static FileInputStream fileInputStream;
	private static Properties userProperties = new Properties();
	
	static {
		try {
			fileInputStream = new FileInputStream(filePath);
			userProperties.load(fileInputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key) {
		return userProperties.getProperty(key);
	}
}
