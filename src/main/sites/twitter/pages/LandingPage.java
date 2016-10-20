package main.sites.twitter.pages;

import org.openqa.selenium.By;

import main.sites.PageObject;
import main.sites.AbstractTrial;

public class LandingPage extends PageObject {
	public final By byButtonSignUp = By.cssSelector(".Button.StreamsSignUp.js-nav.js-signup");
	public final By byButtonLogin0 = By.cssSelector(".Button.StreamsLogin.js-login");
	public final By byButtonLogin1 = By.cssSelector(".submit.btn.primary-btn.js-submit");
	public final By byInputUsername = By.cssSelector("[name='session[username_or_email]']");
	public final By byInputPassword = By.cssSelector("[name='session[password]']");

	public LandingPage(AbstractTrial runner) {
		super(runner);
	}

	public void login(String username, String password) {
		web.click(byButtonLogin0);
		web.sendKeys(byInputUsername, username);
		web.sendKeys(byInputPassword, password);
		web.click(byButtonLogin1);
	}

	public void goToSignUpPage() {
		web.click(byButtonSignUp);
		web.wait.forPageLoad();
	}
}
