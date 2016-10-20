package main.sites.twitter.pages;

import org.openqa.selenium.By;

import main.sites.AbstractTrial;
import main.sites.PageObject;

public class AddPhonePage extends PageObject {
	public final By bySkipLink = By.className("skip-link");

	public AddPhonePage(AbstractTrial runner) {
		super(runner);
	}

	public void clickSkip() {
		web.click(bySkipLink);
		web.wait.forPageLoad();
	}
}
