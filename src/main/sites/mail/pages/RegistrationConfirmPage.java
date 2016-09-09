package main.sites.mail.pages;

import org.openqa.selenium.By;

import main.sites.PageObject;
import main.sites.Trial;

public class RegistrationConfirmPage extends PageObject {
	public final By byButtonContinue = By.cssSelector("input[value='Continue to inbox']");
	public final By byLinkGoToInbox = By.xpath("//*[contains(text(), 'go to inbox')]");

	public RegistrationConfirmPage(Trial runner) {
		super(runner);
	}

}
