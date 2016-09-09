package main.sites.hotnewhiphop;

import org.openqa.selenium.WebDriver;

import main.sites.Trial;
import main.sites.hotnewhiphop.pages.HomePage;

public abstract class HNHipHopTrial extends Trial {

	protected final HomePage homePage = new HomePage(this);

	public HNHipHopTrial(String url) {
		super(url);

	}

	public HNHipHopTrial(WebDriver driver, String url) {
		super(driver, url);
	}
}
