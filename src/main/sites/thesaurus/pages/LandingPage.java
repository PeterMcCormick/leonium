package main.sites.thesaurus.pages;

import org.openqa.selenium.By;

import main.sites.AbstractPageObject;
import main.sites.AbstractTrial;

public class LandingPage extends AbstractPageObject {
	public final By byInputSearch = By.id("q");
	public final By byButtonSearch = By.id("search-submit");

	public LandingPage(AbstractTrial runner) {
		super(runner);
	}

	public void search(String phrase) {
		web.sendKeys(byInputSearch, phrase);
		web.click(byButtonSearch);
		web.wait.forPageLoad();
	}

}
