package main.sites.msn;

import org.openqa.selenium.WebDriver;

import main.sites.AbstractTrial;
import main.sites.msn.pages.LandingPage;

public abstract class AbstractMsnTrial extends AbstractTrial {
	public final LandingPage landingPage = new LandingPage(this);

	public AbstractMsnTrial(String url) {
		super(url);
	}

	public AbstractMsnTrial(WebDriver driver, String url) {
		super(driver, url);
	}

	@Override
	protected void setup() {
	}

}
