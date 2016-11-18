package main.testdomains.samples.instagram.trials;

import org.openqa.selenium.WebDriver;

import main.testdomains.samples.instagram.AbstractInstagramTrial;

public class IGTdemo1 extends AbstractInstagramTrial {

	public IGTdemo1(String url) {
		super(url);
	}
	public IGTdemo1(WebDriver driver, String url) {
		super(driver, url);
	}

	@Override
	protected void test() {
		landingPage.login();
		//userPage.viewUserFollowers("insta.haq");
		//userPage.followUserList();
	}

}