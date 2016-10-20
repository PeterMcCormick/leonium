package main.utils.browserutils;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserBot {
	private final WebDriver driver;
	private final BrowserHandler web;
	private final BrowserLogger logger;
	private final Actions actions;
	private final Robot robot;

	public BrowserBot(BrowserHandler web) {
		this.web = web;
		this.driver = web.driver;
		this.logger = web.logger;
		this.actions = new Actions(driver);
		this.robot = getRobot();
		urlListener().start();
	}

	private Robot getRobot() {
		try {
			return new Robot();
		} catch (AWTException e) {
			return null;
		}
	}

	public void screenshot() { // TODO - return File ?
		RemoteWebDriver rwd = ((RemoteWebDriver) web.driver);
		try {
			rwd.getCommandExecutor().execute(new Command(rwd.getSessionId(), DriverCommand.ELEMENT_SCREENSHOT));
		} catch (IOException e) {
			logger.logStackTrace(e);
		}

	}

	public void activateScreen() {
		// Store the current window handle
		String currentWindowHandle = web.driver.getWindowHandle();

		// run your javascript and alert code
		((JavascriptExecutor) web.driver).executeScript("alert('Test')");
		web.switchTo.alert().accept();

		// Switch back to to the window using the handle saved earlier
		web.switchTo.window(currentWindowHandle);
	}

	public Actions moveToElement(WebElement we) {
		return perform(actions.moveToElement(we));
	}

	private Actions perform(Actions action) {
		action.build().perform();
		return action;
	}

	public Thread urlListener() {
		return new Thread() {
			public void run() {
				try {
					String prevUrl = driver.getCurrentUrl();
					while (true) {
						String currentUrl = driver.getCurrentUrl();
						boolean urlChanged = !currentUrl.equals(prevUrl);
						if (urlChanged) {
							while (!web.getPageLoadState().equals("complete")) {
								continue;
							}
							logger.screenshotPage();
							prevUrl = driver.getCurrentUrl();
						}
					}

				} catch (Exception e) {
				}
			}
		};
	}
}
