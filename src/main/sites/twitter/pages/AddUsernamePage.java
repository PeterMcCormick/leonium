package main.sites.twitter.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import main.sites.AbstractTrial;
import main.sites.PageObject;
import main.utils.Utils;

public class AddUsernamePage extends PageObject {
	public final By byTroucheeSuggestions = By.xpath(".//*[@data-sugg-technik='trochee']");
	public final By byAppendNumbersSuggestions = By.xpath(".//*[@data-sugg-technik='append_numbers']");

	public AddUsernamePage(AbstractTrial runner) {
		super(runner);
	}

	public void clickSuggestion() {
		ArrayList<WebElement> wes = web.getElements(byAppendNumbersSuggestions);
		WebElement we = Utils.randomItem(wes);
		web.click(we);
	}

}
