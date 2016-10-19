package main.utils.browserutils;

import org.openqa.selenium.By;

public abstract class With extends By {
	public static By attributeValueBeing(String attribute, String attributeValue) {
		return By.xpath(String.format("//*[@s='%s')]", attribute, attributeValue));
	}

	public static By attributeValueHaving(String attribute, String attributeValue) {
		return By.xpath(String.format("//*[contains(@s, '%s')]", attribute, attributeValue));
	}

	public static By buttonTextHaving(String attribute, String text) {
		return By.xpath(String.format("//button[contains(text(), '%s')]", text));
	}

	public static By classValueBeing(String classValue) {
		return attributeValueBeing("class", classValue);
	}

	public static By classValueHaving(String classValue) {
		return attributeValueHaving("class", classValue);
	}

	public static By textHaving(String text) {
		return By.xpath(String.format("//*[contains(text(), '%s')]", text));
	}
}
