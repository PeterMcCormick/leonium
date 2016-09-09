package main.sites.instagram.pages;

import org.openqa.selenium.By;

import main.sites.PageObject;
import main.sites.instagram.InstagramTrial;

public class LandingPage extends PageObject {
	public By login = By.linkText("Log in");
	public By username = By.name("username");
	public By password = By.name("password");
	public By signin = By.cssSelector("button");
	public By loginerror = By.id("slfErrorAlert");
	private String url;

	public LandingPage(InstagramTrial trial) {
		super(trial);
		this.url = trial.driver.getCurrentUrl();
	}

	public void login() {
		login("insta.haq", "password0");
	}

	public void login(String user, String pass) {
		forceLogin(user, pass, false);
	}

	public void forceLogin(String user, String pass, boolean isForced) {
		web.deleteAllCookies();
		web.click(login);
		web.sendKeys(username, user);
		web.sendKeys(password, pass);
		web.click(signin);
		if (web.getElement(loginerror) != null && isForced) {
			web.navigateTo(url);
			forceLogin(user, pass, isForced);
		}
	}

}
