package main.sites.twitter.pages;

import org.openqa.selenium.By;

import main.sites.AbstractTrial;
import main.sites.PageObject;

public class SignUpPage extends PageObject {
	public final By byInputFullName = By.id("full-name");
	public final By byInputEmail = By.id("email");
	public final By byInputPassword = By.id("password");
	public final By byButtonSubmit = By.id("submit_button");

	public SignUpPage(AbstractTrial runner) {
		super(runner);
	}

	public void signUp(String fullname, String email, String password) {
		web.sendKeys(byInputFullName, fullname);
		web.sendKeys(byInputEmail, email);
		web.sendKeys(byInputPassword, password);
		web.click(byButtonSubmit);
	}
}
