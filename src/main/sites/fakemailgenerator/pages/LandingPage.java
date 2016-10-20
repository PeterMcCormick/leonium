package main.sites.fakemailgenerator.pages;

import org.openqa.selenium.By;

import main.sites.AbstractTrial;
import main.sites.PageObject;

public class LandingPage extends PageObject {
	public final By byMyEmail = By.id("cxtEmail");

	public LandingPage(AbstractTrial runner) {
		super(runner);
	}

	public final String getMyEmail() {
		return web.getText(byMyEmail);
	}
}
