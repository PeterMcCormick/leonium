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

import com.sun.glass.events.KeyEvent;

import main.utils.Utils;

public class BrowserBot {
	public class UrlListener extends Thread {
		private WebDriver driver;

		public UrlListener(WebDriver driver) {
			this.driver = driver;
		}

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

	private final BrowserHandler web;
	private final BrowserLogger logger;
	private final Actions actions;
	private final Robot robot;
	private final Runtime runtime;

	public BrowserBot(BrowserHandler web) {
		this.web = web;
		this.logger = web.logger;
		this.actions = new Actions(web.driver);
		this.robot = getRobot();
		this.runtime = Runtime.getRuntime();
		new UrlListener(web.driver).start();
	}

	public File screenshotElement(WebElement we) { // TODO - TEST
		moveToElement(we);
		Point p = we.getLocation();
		Dimension d = we.getSize();
		activateScreen();
		BufferedImage bi = robot.createScreenCapture(new Rectangle(p.getX(), p.getY(), d.getWidth(), d.getHeight()));
		String eleName = Utils.removeChars(web.webElementToString(we), "~!@#$&%^*':<>\\/()[]{}") + " - ";
		String fileName = eleName + System.currentTimeMillis() + ".png";
		String directory = logger.getLoggerPath();
		File file = new File(directory + fileName);
		logger.logInfo(logger.getTest().addScreenCapture(file.getName()) + logger.colorTag("green", file.getName()));
		try {
			ImageIO.write(bi, "png", file);
		} catch (IOException e) {
			logger.logException(e);
		}
		return file;
	}

	public void screenshot() { // TODO - return File ?
		RemoteWebDriver rwd = ((RemoteWebDriver) web.driver);
		try {
			rwd.getCommandExecutor().execute(new Command(rwd.getSessionId(), DriverCommand.ELEMENT_SCREENSHOT));
		} catch (IOException e) {
			logger.logException(e);
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

	private Robot getRobot() {
		try {
			return new Robot();
		} catch (AWTException e) {
			return null;
		}
	}
}
