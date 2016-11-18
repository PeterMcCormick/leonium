package main.testdomains.samples.hotnewhiphop.pages;

import org.openqa.selenium.By;

import main.testdomains.samples.AbstractTrial;
import main.testdomains.samples.PageObject;

public class HomePage extends PageObject {
	public final By bySongChart = By.cssSelector(".songChart");
	public final By byButtonMoreSongs = By.cssSelector(".archive-button");

	public HomePage(AbstractTrial runner) {
		super(runner);
	}
}