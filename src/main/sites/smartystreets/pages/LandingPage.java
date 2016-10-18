package main.sites.smartystreets.pages;

import org.openqa.selenium.By;

import main.sites.PageObject;
import main.sites.AbstractTrial;

public class LandingPage extends PageObject {
	public final By byButtonDemo = By.id("#header-demo");

	public LandingPage(AbstractTrial runner) {
		super(runner);
	}

}
