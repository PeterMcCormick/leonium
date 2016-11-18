package main.testdomains.samples.mail.pages;

import org.openqa.selenium.By;

import main.testdomains.samples.AbstractTrial;
import main.testdomains.samples.PageObject;

public class RegistrationConfirmPage extends PageObject {
	public final By byButtonContinue = By.cssSelector("input[value='Continue to inbox']");
	public final By byLinkGoToInbox = By.xpath("//*[contains(text(), 'go to inbox')]");

	public RegistrationConfirmPage(AbstractTrial runner) {
		super(runner);
	}

}
