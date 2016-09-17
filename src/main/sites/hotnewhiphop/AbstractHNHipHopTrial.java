package main.sites.hotnewhiphop;

import org.openqa.selenium.WebDriver;

import main.sites.AbstractTrial;
import main.sites.hotnewhiphop.pages.HomePage;

public abstract class AbstractHNHipHopTrial extends AbstractTrial {

	protected final HomePage homePage = new HomePage(this);

	public AbstractHNHipHopTrial(String url) {
		super(url);

	}

	public AbstractHNHipHopTrial(WebDriver driver, String url) {
		super(driver, url);
	}
}
