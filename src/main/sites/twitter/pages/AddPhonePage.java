package main.sites.twitter.pages;

import org.openqa.selenium.By;

import main.sites.AbstractPageObject;
import main.sites.AbstractTrial;

public class AddPhonePage extends AbstractPageObject {
	public final By bySkipLink = By.className("skip-link");

	public AddPhonePage(AbstractTrial runner) {
		super(runner);
	}

	public void clickSkip() {
		web.click(bySkipLink);
		web.wait.forPageLoad();
	}
}
