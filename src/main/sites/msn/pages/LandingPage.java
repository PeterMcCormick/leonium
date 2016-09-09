package main.sites.msn.pages;

import org.openqa.selenium.By;

import main.sites.PageObject;
import main.sites.AbstractTrial;

public class LandingPage extends PageObject {
	public final By bySearchBar = By.id("q");
	public final By byButtonSearch = By.id("sb_form_go");

	public LandingPage(AbstractTrial runner) {
		super(runner);
	}
}
