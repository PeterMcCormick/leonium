package main.sites.runescape.pages;

import org.openqa.selenium.By;

import main.sites.AbstractPageObject;
import main.sites.AbstractTrial;

public class LandingPage extends AbstractPageObject {
	public final By byHeaderLinks = By.cssSelector(".primary>li>a");

	public LandingPage(AbstractTrial runner) {
		super(runner);
	}

}
