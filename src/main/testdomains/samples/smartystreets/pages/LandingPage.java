package main.testdomains.samples.smartystreets.pages;

import org.openqa.selenium.By;

import main.testdomains.samples.AbstractTrial;
import main.testdomains.samples.PageObject;

public class LandingPage extends PageObject {
	public final By byButtonDemo = By.id("#header-demo");

	public LandingPage(AbstractTrial runner) {
		super(runner);
	}

}
