package main.testdomains.samples.mail.trials;

import org.openqa.selenium.WebDriver;

import main.testdomains.samples.mail.AbstractMailTrial;

public class MailDemo1 extends AbstractMailTrial {

	public MailDemo1(String url) {
		super(url);
	}

	public MailDemo1(WebDriver driver, String url) {
		super(driver, url);
	}

	protected void test() {
		homePage.login("username", "password");
		web.click(homePage.byRecoverEmail);
		web.navigateTo("https://service.mail.com/shareFeedback.html");

		fillForm();
	}

	private void fillForm() {
		feedback.fillForm("Leon Flunter", "0@mail.com", "0@gmail.com", "" + System.currentTimeMillis(), "today");
	}
}