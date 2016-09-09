package main.sites.mail.pages;

import org.openqa.selenium.By;

import main.sites.PageObject;
import main.sites.AbstractTrial;

// https://service.mail.com/shareFeedback.html
public class HomePage extends PageObject {
	public By byButtonLogin = By.id("login-button");
	public By byInputEmail = By.id("login-email");
	public By byInputPassword = By.id("login-password");
	public By byRecoverEmail = By.xpath("html/body/div[1]/div/div[2]/div[1]/div[2]/div/a[1]");
	public By byButtonSubmitLogin = By.cssSelector(".btn.btn-block.login-submit");
	public By byButtonSignUp = By.id("sign-up");

	public HomePage(AbstractTrial runner) {
		super(runner);
	}

	public void login(String email, String password) {
		web.click(byButtonLogin);
		web.sendKeys(byInputEmail, email);
		web.sendKeys(byInputPassword, password);
		web.click(byButtonSubmitLogin);
	}
}
