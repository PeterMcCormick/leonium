package main.sites.hotnewhiphop.pages;

import org.openqa.selenium.By;

import main.sites.PageObject;
import main.sites.AbstractTrial;

public class HomePage extends PageObject {
	public final By bySongChart = By.cssSelector(".songChart");
	public final By byButtonMoreSongs = By.cssSelector(".archive-button");

	public HomePage(AbstractTrial runner) {
		super(runner);
	}
}