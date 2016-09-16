package main.sites.fakemailgenerator.pages;

import org.openqa.selenium.By;

import main.sites.AbstractPageObject;
import main.sites.AbstractTrial;

public class LandingPage extends AbstractPageObject {
	public final By byMyEmail = By.id("cxtEmail");

	public LandingPage(AbstractTrial runner) {
		super(runner);
	}

	public final String getMyEmail() {
		return web.getText(byMyEmail);
	}
}
