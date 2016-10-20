package main.sites.throwawaymail.pages;

import org.openqa.selenium.By;

import main.sites.AbstractTrial;
import main.sites.PageObject;

public class LandingPage extends PageObject {
	public final By byEmail = By.id("email");

	public LandingPage(AbstractTrial runner) {
		super(runner);
	}

	public String getEmail() {
		return web.getText(byEmail);
	}

}
