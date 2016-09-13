package main.utils.browserutils;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;

import main.gui.MyGui;
import main.utils.Utils;

public class BrowserBot {
	public final MyGui brain = new MyGui();
	private final WebDriver driver;
	private final BrowserHandler web;
	private final BrowserLogger logger;
	private final Actions actions;
	private final Robot robot;

	public BrowserBot(BrowserHandler web) {
		this.web = web;
		this.logger = web.logger;
		this.driver = web.driver;
		this.actions = new Actions(web.driver);
		this.robot = getRobot();
		new Listener().start();
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

	public void displayImage(File file) {
		brain.setActiveElementImage(file.getAbsolutePath());
	}

	private Robot getRobot() {
		try {
			return new Robot();
		} catch (AWTException e) {
			return null;
		}
	}

	public Actions moveToElement(WebElement we) {
		return perform(actions.moveToElement(we));
	}

	private Actions perform(Actions action) {
		action.build().perform();
		return action;
	}

	public class Listener extends Thread {

		public void run() {
			try {
				String prevUrl = driver.getCurrentUrl();
				while (true) {
					String currentUrl = driver.getCurrentUrl();
					if (!currentUrl.equals(prevUrl)) {
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
	}
}
