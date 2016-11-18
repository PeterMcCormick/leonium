package main.testdomains.samples.mail;

import org.openqa.selenium.WebDriver;

import main.testdomains.samples.AbstractTrial;
import main.testdomains.samples.mail.pages.ContactInfoPage;
import main.testdomains.samples.mail.pages.FeedbackPage;
import main.testdomains.samples.mail.pages.HomePage;
import main.testdomains.samples.mail.pages.RegistrationConfirmPage;
import main.testdomains.samples.mail.pages.SignUpPage;

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
