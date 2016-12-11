package main.sites.instagram.pages;

import org.openqa.selenium.By;

import main.sites.PageObject;
import main.sites.instagram.AbstractInstagramTrial;

public class LandingPage extends PageObject {
	public By byInputLogin = By.linkText("Log in");
	public By byInputUsername = By.name("username");
	public By byInputPassword = By.name("password");
	public By byButtonSignin = By.cssSelector("button");
	public By byErrorLogin = By.id("slfErrorAlert");

	public LandingPage(AbstractInstagramTrial trial) {
		super(trial);
	}

	public void login() {
		login("insta.haq", "password0");
	}

	public void login(String user, String pass) {
		forceLogin(user, pass, false);
	}

	public void forceLogin(String user, String pass, boolean isForced) {
		web.deleteAllCookies();
		web.click(byInputLogin);
		web.sendKeys(byInputUsername, user);
		web.sendKeys(byInputPassword, pass);
		web.click(byButtonSignin);
		if (web.getElement(byErrorLogin) != null && isForced) {
			web.navigateTo(trial.driver.getCurrentUrl());
			forceLogin(user, pass, isForced);
		}
	}

}
