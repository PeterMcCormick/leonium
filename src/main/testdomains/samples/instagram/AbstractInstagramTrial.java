package main.testdomains.samples.instagram;

import org.openqa.selenium.WebDriver;

import main.testdomains.samples.AbstractTrial;
import main.testdomains.samples.instagram.pages.LandingPage;
import main.testdomains.samples.instagram.pages.UserPage;

public abstract class AbstractInstagramTrial extends AbstractTrial {

	// ensures the creation of only one page object per specific page
	protected LandingPage landingPage;
	protected UserPage userPage;

	public AbstractInstagramTrial(String url) {
		super(url);
	}

	public AbstractInstagramTrial(WebDriver driver, String url) {
		super(driver, url);
		userPage = new UserPage(this);
		landingPage = new LandingPage(this);
	}

	protected abstract void test();

	protected void setup() {
	}
}