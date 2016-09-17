package main.sites.msn.trials;

import org.openqa.selenium.WebDriver;

import main.sites.msn.AbstractMsnTrial;

public class MsnDemo1 extends AbstractMsnTrial {
	public MsnDemo1() {
		super("http://www.msn.com/");
	}

	public MsnDemo1(WebDriver driver, String url) {
		super(driver, url);
	}

	@Override
	protected void test() {
		web.navigateTo(initialUrl);
		web.sendKeys(landingPage.bySearchBar, "Hello world!");
		web.click(landingPage.byButtonSearch);
		// web.wait.forPageLoad(30);
		// logger.screenshotPage(driver.getCurrentUrl());
	}
}
