package main.utils.browserutils;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import main.utils.Utils;

public class BrowserWaitt {
	private final BrowserHandler web;
	private final BrowserLogger logger;
	private final WebDriver driver;

	public BrowserWaitt(BrowserHandler web) {
		this.web = web;
		this.driver = web.driver;
		this.logger = web.logger;
	}

	public boolean forAlert(int waitSeconds) {
		return waitUntil(waitSeconds, ExpectedConditions.alertIsPresent());
	}

	public WebElement forClickability(By by, int waitSeconds) {
		long t0 = System.currentTimeMillis();
		waitUntil(waitSeconds, ExpectedConditions.elementToBeClickable(by));
		return forPresence(by, remainingTime(waitSeconds, t0));
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
			} else if (condition.contains("disp")) {
				we = forDisplayed(by, remainingTime, true);
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

	public WebElement forKeyable(By by, int waitSeconds) {
		Utils.print("forKeyable(%s,%s)", by, waitSeconds);
		boolean isKeyable = false;
		long t0 = System.currentTimeMillis();

		WebElement we = forPresence(by, waitSeconds);
		Utils.printElapsedTime(t0);

		try {
			we.sendKeys(" ", Keys.BACK_SPACE);
			isKeyable = true;
		} catch (Exception e) {
			String details = "Waiting for element to be keyable ";
			logger.logDataRetrieval(details + "[" + web.webElementToString(we) + "]");
			while (!isKeyable && Utils.getElapsedTime(t0) < remainingTime(waitSeconds, t0)) {
				try {
					we.sendKeys(" ", Keys.BACK_SPACE);
					isKeyable = true;
					break;
				} catch (Exception ee) {
					Utils.printElapsedTime(t0);
					continue;
				}
			}
		}
		logger.logMinorEvent(isKeyable, "[" + web.webElementToString(we) + "] is keyable");
		return we;
	}

	public WebElement forEnabled(By by, int waitSeconds, boolean isEnabled) {
		long t0 = System.currentTimeMillis();
		WebElement we = forPresence(by, waitSeconds);
		if (we.isEnabled() != isEnabled) {
			String details = "Waiting for element to be " + (isEnabled ? "en" : "dis") + "abled ";
			logger.logDataRetrieval(details + "[" + web.webElementToString(we) + "]");
			while (we.isEnabled() != isEnabled && Utils.getElapsedTime(t0) < remainingTime(waitSeconds, t0)) {
				continue;
			}
			web.isEnabled(we);
		}
		return we;
	}

	public WebElement forInvisibility(By by, int waitSeconds) {
		long t0 = System.currentTimeMillis();
		waitUntil(waitSeconds, ExpectedConditions.invisibilityOfElementLocated(by));
		return forPresence(by, remainingTime(waitSeconds, t0));
	}

	public WebElement forNotStale(By by, int waitSeconds) {
		long t0 = System.currentTimeMillis();
		WebElement we = forPresence(by, waitSeconds);
		try {
			we.getText();
		} catch (StaleElementReferenceException stere) {
			waitUntil(remainingTime(waitSeconds, t0), ExpectedConditions.not(ExpectedConditions.stalenessOf(we)));
		} catch (NullPointerException npe) {
		}
		return we;
	}

	public boolean forPageState(String desiredState, int waitSeconds) {
		if (web.getPageLoadState().equals(desiredState)) {
			return true;
		}
		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			private String pageState = null;

			public Boolean apply(WebDriver driver) {
				pageState = web.getPageLoadState();
				return pageState.equals("complete");
			}

			public String toString() {
				if (!web.getPageLoadState().equals(desiredState)) {
					logger.logDataRetrieval("Current webpage loading-state is \"" + pageState + "\"");
				}
				return "webpage state to be \"" + desiredState + "\"";
			}
		};
		return waitUntil(waitSeconds, function);
	}

	public boolean forPageLoad(int waitSeconds) {
		return forPageState("complete", waitSeconds);
	}

	public WebElement forPresence(By by, int waitSeconds) {
		try {
			return driver.findElement(by);
		} catch (NoSuchElementException nse) {
			waitUntil(waitSeconds, ExpectedConditions.presenceOfElementLocated(by));
			return driver.findElement(by);
		}
	}

	// wait for presence of all WebElements by byType
	public ArrayList<WebElement> forPresences(int waitSeconds, By by) {
		waitUntil(waitSeconds, ExpectedConditions.presenceOfAllElementsLocatedBy(by));
		return (ArrayList<WebElement>) driver.findElements(by);
	}

	public boolean forUrlToContain(int waitSeconds, String partUrl) {
		ExpectedCondition<Boolean> urlContains = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				String currentUrl = driver.getCurrentUrl();
				if (currentUrl != null && partUrl != null) {
					boolean curContainsPart = currentUrl.contains(partUrl);
					boolean partContainsCur = partUrl.contains(currentUrl);
					return currentUrl != null && (curContainsPart || partContainsCur);
				}
				return false;
			}

			@Override
			public String toString() {
				return "url to contain \"" + partUrl + "\". Current url: \"" + driver.getCurrentUrl() + "\"";
			}
		};
		return waitUntil(waitSeconds, urlContains);
	}

	// wait for visibility of multiple WebElements
	public ArrayList<WebElement> forVisibilities(int waitSeconds, ArrayList<WebElement> elements) {
		waitUntil(waitSeconds, ExpectedConditions.visibilityOfAllElements(elements));
		return elements;
	}

	public WebElement forVisibility(By by, int waitSeconds) {
		long t0 = System.currentTimeMillis();
		WebElement we = forPresence(by, waitSeconds);
		if (we != null && !we.isDisplayed()) {
			waitUntil(remainingTime(waitSeconds, t0), ExpectedConditions.visibilityOfElementLocated(by));
		}
		return we;
	}

	public WebElement forDisplayed(By by, int waitSeconds, boolean isDisplayed) {
		long t0 = System.currentTimeMillis();
		WebElement we = forPresence(by, waitSeconds);
		if (we.isDisplayed() != isDisplayed) {
			String details = "Waiting for element to be " + (isDisplayed ? "en" : "dis") + "abled ";
			logger.logDataRetrieval(details + "[" + web.webElementToString(we) + "]");
			while (we.isDisplayed() != isDisplayed && Utils.getElapsedTime(t0) < remainingTime(waitSeconds, t0)) {
				continue;
			}
			web.isDisplayed(we);
		}
		return we;

	}

	private int remainingTime(long baseTime, long t0) {
		return (int) (baseTime - Utils.getElapsedTime(t0));
	}

	// wait for specified condition to return true
	private <T> boolean waitUntil(int waitSeconds, Function<WebDriver, T> condition) {
		if (waitSeconds > 0) {
			try {
				boolean currentCondition = (boolean) condition.apply(driver);
				if (currentCondition) {
					return currentCondition;
				}
			} catch (InvalidSelectorException | ClassCastException e) {
			}

			logger.logDataRetrieval("Waiting for " + web.webElementToString(condition.toString()));
			WebDriverWait wait = new WebDriverWait(driver, waitSeconds);
			try {
				try {
					wait.until(ExpectedConditions.refreshed((ExpectedCondition<T>) condition));
				} catch (ClassCastException cce) {
					wait.until(condition);
				}
				return true;
			} catch (UnhandledAlertException | TimeoutException e) {
				if (web.options.continueOnException.getValue()) {
					logger.logException(e);
					return false;
				}
				throw e;
			}
		}
		return false;
	}
}