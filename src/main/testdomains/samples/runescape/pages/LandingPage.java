package main.testdomains.samples.runescape.pages;

import org.openqa.selenium.By;

import main.testdomains.samples.AbstractTrial;
import main.testdomains.samples.PageObject;

public class LandingPage extends PageObject {
	public final By byHeaderLinks = By.cssSelector(".primary>li>a");

	public LandingPage(AbstractTrial runner) {
		super(runner);
	}

}
