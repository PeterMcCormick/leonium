package main.testdomains.samples.mail.pages;

import org.openqa.selenium.By;

import main.testdomains.samples.AbstractTrial;
import main.testdomains.samples.PageObject;

public class ContactInfoPage extends PageObject {
	public By contactEmail = By.id("id5a");
	public By submit = By.id("id6c");
	public By contactForm = By.id("#id85");

	public ContactInfoPage(AbstractTrial runner) {
		super(runner);
	}
}
