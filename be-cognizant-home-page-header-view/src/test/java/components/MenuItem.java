package components;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This is a helper class, It's Object represents a menu Item that is present on the Be cognizant home page. Any such Menu Item may have a title, an link it navigates to and sub menu it gives the access to.
 * </p>
 * @author 2308990
 *
 */
public class MenuItem {
	public String link;
	public String text;
	public List<MenuItem> subMenuItems;
	
	/**
	 * instantiate menu item object with empty list
	 */
	public MenuItem() {
		subMenuItems = new ArrayList<>();
	}
	
	/**
	 * instantiates Menu Item object with the given link, text and an empty list for sub menu items
	 * @param link
	 * @param text
	 */
	public MenuItem(String link, String text) {
		this();
		this.link = link;
		this.text = text;
	}

	@Override
	public String toString() {
		return "MenuItem [text=" + text + ", subMenuItems=" + subMenuItems + "]";
	}

}
