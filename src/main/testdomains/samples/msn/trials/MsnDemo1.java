package main.testdomains.samples.msn.trials;

import org.openqa.selenium.WebDriver;

import main.testdomains.samples.msn.AbstractMsnTrial;

public class MsnDemo1 extends AbstractMsnTrial {
	public MsnDemo1(String url) {
		super(url);
	}

	public MsnDemo1(WebDriver driver, String url) {
		super(driver, url);
	}

	@Override
	protected void test() {
		web.navigateTo(url);
		web.sendKeys(landingPage.bySearchBar, "Hello world!");
		web.click(landingPage.byButtonSearch);
		// web.wait.forPageLoad(30);
		// logger.screenshotPage(driver.getCurrentUrl());
	}
}
