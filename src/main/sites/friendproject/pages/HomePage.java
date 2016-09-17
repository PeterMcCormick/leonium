package main.sites.friendproject.pages;

import org.openqa.selenium.By;

import main.sites.AbstractPageObject;
import main.sites.AbstractTrial;

public class HomePage extends AbstractPageObject {
	public final By byInputEmail1 = By.xpath("(.//input[@name='email'])[1]");
	public final By byInputPassword1 = By.xpath("(.//input[@name='password'])[1]");
	public final By byButtonLogIn = By.cssSelector("input[value='Log In']");

	public final By byInputFirstName = By.cssSelector("input[name='first_name']");
	public final By byInputLastName = By.cssSelector("input[name='last_name']");
	public final By byInputEmail2 = By.xpath("(.//input[@name='email'])[2]");
	public final By byInputPassword2 = By.xpath("(.//input[@name='password'])[2]");
	public final By byButtonSignUp = By.cssSelector("input[@value='Sign Up']");

	public HomePage(AbstractTrial runner) {
		super(runner);
	}

	public void login(String username, String password) {
		web.sendKeys(byInputEmail1, "HelloWorld@pobox.com");
		web.sendKeys(byInputPassword1, "password0");
	}

	public void signUp(String firstname, String lastname, String email, String password) {
		web.sendKeys(byInputFirstName, firstname);
		web.sendKeys(byInputLastName, lastname);
		web.sendKeys(byInputEmail2, email);
		web.sendKeys(byInputPassword2, password);
	}
}
