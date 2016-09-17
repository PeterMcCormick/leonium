package main.sites.instagram.trials;

import org.openqa.selenium.WebDriver;

import main.sites.instagram.AbstractInstagramTrial;

public class IGTdemo1 extends AbstractInstagramTrial {

	public IGTdemo1() {
		super("https://www.instagram.com/");
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