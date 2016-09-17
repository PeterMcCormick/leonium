package main.sites.mail;

import org.openqa.selenium.WebDriver;

import main.sites.AbstractTrial;
import main.sites.mail.pages.ContactInfoPage;
import main.sites.mail.pages.FeedbackPage;
import main.sites.mail.pages.HomePage;
import main.sites.mail.pages.RegistrationConfirmPage;
import main.sites.mail.pages.SignUpPage;

public abstract class AbstractMailTrial extends AbstractTrial {
	protected final SignUpPage signUpPage = new SignUpPage(this);
	protected final RegistrationConfirmPage registrationConfirmPage = new RegistrationConfirmPage(this);
	protected final HomePage homePage = new HomePage(this);;
	protected final FeedbackPage feedback = new FeedbackPage(this);
	protected final ContactInfoPage contactInfo = new ContactInfoPage(this);

	public AbstractMailTrial(String url) {
		super(url);
	}

	public AbstractMailTrial(WebDriver driver, String url) {
		super(driver, url);
	}

	@Override
	protected void setup() {
	}

	protected abstract void test();

}
