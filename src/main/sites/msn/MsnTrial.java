package main.sites.msn;

import org.openqa.selenium.WebDriver;

import main.sites.Trial;
import main.sites.msn.pages.LandingPage;

public abstract class MsnTrial extends Trial {
	public final LandingPage landingPage = new LandingPage(this);

	public MsnTrial(String url) {
		super(url);
	}

	public MsnTrial(WebDriver driver, String url) {
		super(driver, url);
	}

	@Override
	protected void setup() {
	}

}
