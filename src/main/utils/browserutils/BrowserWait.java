package main.utils.browserutils;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import main.utils.Utils;

public class BrowserWait {

	protected final BrowserHandler web;
	protected final BrowserReports logger;
	protected final WebDriver driver;

	public BrowserWait(BrowserHandler web) {
		this.web = web;
		this.driver = web.driver;
		this.logger = web.logger;
	}

	public WebElement forEnabled(By by, int waitSeconds, boolean isEnabled) {
		long t0 = System.currentTimeMillis();
		WebElement we = forPresence(by, waitSeconds);
		if (timeRemains(waitSeconds, t0) && we != null && we.isEnabled() != isEnabled) {
			String details = "Waiting for element to be " + (isEnabled ? "en" : "dis") + "abled ";
			logger.logDataRetrieval(details + "[" + web.webElementToString(we) + "]");
			while (we.isEnabled() != isEnabled && timeRemains(waitSeconds, t0)) {
				continue;
			}
			web.isEnabled(we);
		}
		return we;
	}

	public WebElement forVisibility(By by, int waitSeconds) {
		long t0 = System.currentTimeMillis();
		WebElement we = forPresence(by, waitSeconds);
		if (we != null && !we.isDisplayed()) {
			waitUntil(remainingTime(waitSeconds, t0), ExpectedConditions.visibilityOfElementLocated(by));
		}
		return we;
	}

	public WebElement forInvisibility(By by, int waitSeconds) {
		long t0 = System.currentTimeMillis();
		waitUntil(waitSeconds, ExpectedConditions.invisibilityOfElementLocated(by));
		return forPresence(by, remainingTime(waitSeconds, t0));
	}

	public WebElement forClickability(By by, int waitSeconds) {
		long t0 = System.currentTimeMillis();
		waitUntil(waitSeconds, ExpectedConditions.elementToBeClickable(by));
		return forPresence(by, remainingTime(waitSeconds, t0));
	}

	public WebElement forPresence(By by, int waitSeconds) {
		WebElement we = null;
		try {
			try {
				we = driver.findElement(by);
			} catch (NoSuchElementException nsee) {
				waitUntil(waitSeconds, ExpectedConditions.presenceOfElementLocated(by));
				we = driver.findElement(by);
			}
		} catch (NoSuchElementException | InvalidElementStateException e) {
			if (!web.options.continueOnNoSuchElement.getValue()) {
				throw e;
			}
		}
		return we;
	}

	public WebElement forNotStale(By by, int waitSeconds) {
		long t0 = System.currentTimeMillis();
		WebElement we = forPresence(by, waitSeconds);
		try {
			we.getText();
		} catch (StaleElementReferenceException stere) {
			waitUntil(remainingTime(waitSeconds, t0), ExpectedConditions.not(ExpectedConditions.stalenessOf(we)));
		}
		return we;
	}

	public boolean forAlert(int waitSeconds) {
		return waitUntil(waitSeconds, ExpectedConditions.alertIsPresent());
	}

	public boolean forUrlToContain(int waitSeconds, String partUrl) {
		String currentUrl = null;
		ExpectedCondition<Boolean> urlContains = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				String currentUrl = driver.getCurrentUrl();
				return currentUrl != null && currentUrl.contains(partUrl);
			}

			public String toString() {
				return String.format("url to contain \"%s\". Current url: \"%s\"", partUrl, driver.getCurrentUrl());
			}
		};

		currentUrl = driver.getCurrentUrl();
		if (urlContains.apply(driver).booleanValue() && currentUrl != null && currentUrl.contains(partUrl)) {
			return true;
		}
		return waitUntil(waitSeconds, urlContains);
	}

	// wait for visibility of multiple WebElements
	public ArrayList<WebElement> forVisibilities(int waitSeconds, ArrayList<WebElement> elements) {
		waitUntil(waitSeconds, ExpectedConditions.visibilityOfAllElements(elements));
		return elements;
	}

	// wait for presence of all WebElements by byType
	public ArrayList<WebElement> forPresences(int waitSeconds, By by) {
		waitUntil(waitSeconds, ExpectedConditions.presenceOfAllElementsLocatedBy(by));
		return (ArrayList<WebElement>) driver.findElements(by);
	}

	public boolean forPageLoad(int waitSeconds) {
		return forPageState(waitSeconds, "complete");
	}

	public boolean forPageLoad() {
		return forPageLoad(web.options.defaultWait.getValue());
	}

	public boolean forPageState(int waitSeconds, String state) {
		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				String returnVal = jse.executeScript("return document.readyState").toString();
				logger.logDataRetrieval("Current webpage loading-state is \"" + returnVal + "\"");
				return returnVal.equals(state);
			}

			public String toString() {
				return " webpage to load-state to be \"" + state + "\"";
			}
		};
		return function.apply(driver).booleanValue() ? true : waitUntil(waitSeconds, function);
	}

	public WebElement forConditions(By by, int waitSeconds, String... conditions) {
		WebElement we = null;
		int remainingTime = -1;
		long t0 = System.currentTimeMillis();
		for (String condition : conditions) {
			remainingTime = remainingTime(waitSeconds, t0);
			condition = condition.toLowerCase();
			if (!Utils.strsNotNull(condition)) {
				continue;
			} else if (condition.contains("invis")) {
				we = forInvisibility(by, remainingTime);
			} else if (condition.contains("visib")) {
				we = forVisibility(by, remainingTime);
			} else if (condition.contains("click")) {
				we = forClickability(by, remainingTime);
			} else if (condition.contains("enab")) {
				we = forEnabled(by, remainingTime, true);
			} else if (condition.contains("disab")) {
				we = forEnabled(by, remainingTime, false);
			} else if (condition.contains("pres")) {
				we = forPresence(by, remainingTime);
			} else if (condition.contains("stale")) {
				we = forNotStale(by, remainingTime);
			}
		}
		return we = we != null ? we : forPresence(by, remainingTime);
	}

	// wait for specified condition to return true
	private <T> boolean waitUntil(int waitSeconds, Function<WebDriver, T> condition) {
		boolean outcome = false;
		try {
			if (waitSeconds > 0) {
				logger.logDataRetrieval("Waiting for " + web.webElementToString(condition.toString()));
				WebDriverWait wait = new WebDriverWait(driver, waitSeconds);
				try {
					wait.until(ExpectedConditions.refreshed((ExpectedCondition<T>) condition));
				} catch (ClassCastException cce) {
					wait.until(condition);
				}
				outcome = true;
			}
		} catch (TimeoutException toe) {
			if (!web.options.continueOnTimeout.getValue()) {
				throw toe;
			}
		}
		return outcome;
	}

	public WebElement forKeyable(By by, int waitSeconds) {
		Utils.print("forKeyable(%s,%s)", by, waitSeconds);
		boolean isKeyable = false;
		long t0 = System.currentTimeMillis();

		WebElement we = forPresence(by, waitSeconds);

		try {
			we.sendKeys(" ", Keys.BACK_SPACE);
			isKeyable = true;
		} catch (Exception e) {
			String details = "Waiting for element to be keyable ";
			logger.logDataRetrieval(details + "[" + web.webElementToString(we) + "]");
			while (!isKeyable && timeRemains(waitSeconds, t0)) {
				try {
					we.sendKeys(" ", Keys.BACK_SPACE);
					isKeyable = true;
					break;
				} catch (Exception ee) {
					continue;
				}
			}
			logger.logMinorEvent(isKeyable, "[" + web.webElementToString(we) + "] is keyable");
		}
		return we;
	}

	private int remainingTime(long baseSeconds, long t0) {
		return (int) (baseSeconds + 1 - Utils.getElapsedTime(t0));
	}

	private boolean timeRemains(long baseSeconds, long t0) {
		return remainingTime(baseSeconds, t0) > 0;
	}
}