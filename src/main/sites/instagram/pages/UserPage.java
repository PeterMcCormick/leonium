package main.sites.instagram.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import main.sites.PageObject;
import main.sites.AbstractTrial;

public class UserPage extends PageObject {
	public By followerList = By.xpath("//span[@id='react-root']/section/main/article/header/div[2]/ul/li[2]/a/span[3]");
	public By followers = By.cssSelector("._jvpff._k2yal._csba8._k0ujq._nv5lf");

	public UserPage(AbstractTrial runner) {
		super(runner);
	}

	public boolean viewUser(String username) {
		return web.navigateTo("instagram.com/" + username + "/");
	}

	public void viewUserFollowers(String username) {
		viewUser(username);
		web.click(followerList);
	}

	public void followUserList() {
		List<WebElement> followButtons = web.getElements(followers);
		while (true) {
			for (WebElement followButton : followButtons) {
				try {
					String status = followButton.getText().toLowerCase();
					if (status.equals("follow")) {
						web.click(followButton);
					}
					web.sendKeys(followButton, Keys.PAGE_DOWN.name());
					followButtons = web.getElements(followers);
				} catch (Exception e) {
					runner.logger.logStackTrace(e);
				}
			}
		}
	}
}