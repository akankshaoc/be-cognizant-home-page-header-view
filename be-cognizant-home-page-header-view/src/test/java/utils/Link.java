package utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Link {
	public static int getHttpResponseCode(String url) throws MalformedURLException, IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("HEAD");
		connection.connect();
		return connection.getResponseCode();
	}
}
