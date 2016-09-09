package main.sites.mail.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import main.sites.PageObject;
import main.sites.Trial;

public class FeedbackPage extends PageObject {
	public final By byFields = By.cssSelector("input[id *= 'id'], select[id *= 'id'], textarea[id *= 'id']");

	public FeedbackPage(Trial runner) {
		super(runner);
	}

	public void fillForm(String name, String mailEmail, String contactEmail, String message, String lastLogin) {
		List<WebElement> fields = web.getElements(byFields);
		web.sendKeys(fields.get(0), name);
		web.sendKeys(fields.get(1), mailEmail);
		web.sendKeys(fields.get(2), contactEmail);
		web.selectByIndex(fields.get(3), 1);
		web.sendKeys(fields.get(4), message);
		web.sendKeys(fields.get(5), web.getBrowserAndVersion());
		web.sendKeys(fields.get(6), lastLogin);
		web.selectByIndex(fields.get(7), 1);
		web.click(fields.get(8));
	}
}