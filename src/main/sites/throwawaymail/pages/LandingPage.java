package main.sites.throwawaymail.pages;

import org.openqa.selenium.By;

import main.sites.AbstractPageObject;
import main.sites.AbstractTrial;

public class LandingPage extends AbstractPageObject {
	public final By byEmail = By.id("email");

	public LandingPage(AbstractTrial runner) {
		super(runner);
	}

	public String getEmail() {
		return web.getText(byEmail);
	}

}
