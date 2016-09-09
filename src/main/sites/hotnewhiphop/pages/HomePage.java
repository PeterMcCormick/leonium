package main.sites.hotnewhiphop.pages;

import org.openqa.selenium.By;

import main.sites.AbstractPageObject;
import main.sites.AbstractTrial;

public class HomePage extends AbstractPageObject {
	public final By bySongChart = By.cssSelector(".songChart");
	public final By byButtonMoreSongs = By.cssSelector(".archive-button");

	public HomePage(AbstractTrial runner) {
		super(runner);
	}
}