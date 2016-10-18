package main.utils.browserutils;

import org.openqa.selenium.By;

public abstract class With extends By {
	public static By text(String text) {
		return By.xpath(String.format("//*[contains(text(), '%s')]", text));
	}

}
