package main.utils.browserutils;

import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.google.common.base.Function;

import main.sites.AbstractTrial;
import main.utils.Utils;

public class BrowserHandler {

	public final WebDriver driver;
	public final JavascriptExecutor jse;
	public final BrowserLogger logger;
	public final BrowserWait wait;
	public final BrowserBot bot;
	public final TargetLocator switchTo;
	public final BrowserHandlerOptions options;
	private int defaultWait;

	@SuppressWarnings("unused")
	private BrowserHandler() {
		this(null, null, -1);
	}

	public BrowserHandler(AbstractTrial validator) {
		this(validator, 15);
	}

	public BrowserHandler(AbstractTrial validator, int defaultWait) {
		this(validator.driver, validator.logger, defaultWait);
	}

	public BrowserHandler(WebDriver driver, BrowserLogger logger) {
		this(driver, logger, 15);
	}

	public BrowserHandler(WebDriver driver, BrowserLogger logger, int defaultWait) {
		this.driver = driver;
		this.logger = logger;
		this.switchTo = driver.switchTo();
		this.defaultWait = defaultWait;
		this.jse = ((JavascriptExecutor) driver);

		this.options = new BrowserHandlerOptions();
		this.wait = new BrowserWait(this);
		this.bot = new BrowserBot(this);

		options.defaultWait.setValue(defaultWait);
		options.continueOnException.setValue(true);
	}

	// click by byType
	public boolean click(By by) {
		return click(wait.forConditions(by, options.defaultWait.getValue(), "presence", "visible", "enabled",
				"clickable", "stale"));
	}

	// click by WebElement
	public boolean click(WebElement we) {
		boolean success = false;
		try {
			logScreenshotElement(we);
			we.click();
			success = true;
		} catch (Exception e) {
			logger.logException(e);
		}
		return logger.logMinorEvent(success, "Clicked [" + webElementToString(we) + "]");
	}

	// switch to and close pop up
	public void closePopup() {
		try {
			String parent = switchToPopup();
			logger.logInfo("Closing window: [" + driver.getCurrentUrl() + "]");
			switchTo.alert().dismiss();
			switchTo.window(parent);
		} catch (Exception e) {
			logger.logException(e);
		}
	}

	// switch to and close all pop ups
	public void closePopups() {
		try {
			TargetLocator target = driver.switchTo();
			String parent = driver.getWindowHandle();
			Iterator<String> handles = driver.getWindowHandles().iterator();
			while (handles.hasNext()) {
				String next = handles.next();
				target.window(next);
				Utils.print("Closing window: [" + driver.getTitle() + "]\n<br />[" + driver.getCurrentUrl() + "]");
				target.window(next).close();
			}
			target.window(parent);

		} catch (Exception e) {
			logger.logException(e);
		}
	}

	// deletes all cached cookies and logs each deletion
	public void deleteAllCookies() {
		Options driverManager = driver.manage();
		logger.logInfo("Deleting all cookies");
		for (Cookie cookie : driverManager.getCookies()) {
			logger.logInfo("Deleting cookie \"" + cookie.getName() + "\"");
		}
		driverManager.deleteAllCookies();
	}

	// return true if specified element contains specified text
	public boolean elementContainsText(By by, String expectedText) {
		return elementContainsText(getElement(by), expectedText);
	}

	// return true if specified element contains specified text
	public boolean elementContainsText(WebElement we, String expectedText) {
		boolean outcome = false;
		try {
			outcome = getText(we).contains(expectedText);
		} catch (Exception e) {
			outcome = getElementByText(expectedText) != null;
		}
		return logger.logMinorEvent(outcome, "[" + webElementToString(we) + "] contained text '" + expectedText + "'");
	}

	public WebElement getActiveElement() {
		return switchTo.activeElement();
	}

	// retrieve value of specified attribute from specified By selector
	public String getAttributeValue(By by, String attribute) {
		return getAttributeValue(getElement(by), attribute);
	}

	// retrieve value of specified attribute from specified Web Element
	public String getAttributeValue(WebElement we, String attribute) {
		String val = null;
		try {
			val = we.getAttribute(attribute);
		} catch (WebDriverException ster) {
			val = getElement(toByVal(we)).getAttribute(attribute);
		} catch (Exception e) {
			logger.logException(e);
		}
		return val;
	}

	// return browser and version of currently instantiated driver
	public String getBrowserAndVersion() {
		Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
		String browser = "Browser: " + capabilities.getBrowserName();
		String version = "Version: " + capabilities.getVersion();
		return browser + " || " + version;
	}

	// return non-stale WebElement specified by byType
	public WebElement getElement(By by) {
		WebElement we = wait.forConditions(by, options.defaultWait.getValue(), "presence", "stale");
		logger.logMinorEvent(we != null, "Located element [" + webElementToString(we) + "]");
		return we;
	}

	// return first element containing specified text-value
	public WebElement getElementByText(String text) {
		return getElement(By.xpath("//*[contains(text(), '" + text + "')]"));
	}

	public WebElement[] getElements(By... bys) {
		int n = bys.length;
		WebElement[] elements = new WebElement[n];
		for (int i = 0; i < n; i++) {
			try {
				elements[i] = getElement(bys[i]);
			} catch (Exception e) {
				if (!options.continueOnException.getValue()) {
					throw e;
				}
				logger.logException(e);
			}
		}
		return elements;
	}

	// return list of WebElements with specified byType
	public ArrayList<WebElement> getElements(By by) {
		ArrayList<WebElement> elements = wait.forPresences(options.defaultWait.getValue(), by);
		int count = elements.size();
		logger.logMinorEvent(count > 0, "Located " + count + " elements [" + by + "]");
		return elements;
	}

	public WebElement[] getElements(List<By> bys) {
		return getElements(bys.toArray(new By[bys.size()]));
	}

	// return list of elements containing specified text-value
	public ArrayList<WebElement> getElementsByText(String text) {
		return getElements(By.xpath("//*[contains(text(), '" + text + "')]"));
	}

	public WebElement[] getElementsFromWindow(String windowUrl, By... bys) {
		WebElement[] elements = new WebElement[bys.length];
		String parentWindow = driver.getWindowHandle();
		TargetLocator switchTo = driver.switchTo();
		int defaultWait = options.defaultWait.getValue();

		for (String currentWindow : driver.getWindowHandles()) {
			switchTo.window(currentWindow);
			try {
				wait.forUrlToContain(3, windowUrl);
				if (driver.getCurrentUrl().contains(windowUrl)) {
					options.defaultWait.setValue(3);
					elements = getElements(bys);
				}
			} catch (NoSuchElementException | TimeoutException e) {
				continue;
			}
		}
		options.defaultWait.setValue(defaultWait);
		switchTo.defaultContent();
		switchTo.window(parentWindow);
		return elements;
	}

	// return list of WebElements with specified attribute
	public ArrayList<WebElement> getElementsWithAttribute(String attribute) {
		return getElements(By.cssSelector("[" + attribute + "]"));
	}

	// return list of WebElements with specified attribute
	public ArrayList<WebElement> getElementsWithAttributeValue(String attribute, String value) {
		return getElements(By.xpath(".//*[@" + attribute + "=" + "'" + value + "']"));
	}

	// return list of WebElement links
	public ArrayList<WebElement> getLinks() {
		return getTagElementsWithAttribute("a", "href");
	}

	public String getPageLoadState() {
		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			public String state;

			public Boolean apply(WebDriver driver) {
				state = jse.executeScript("return document.readyState").toString();
				return true;
			}

			public String toString() {
				return state;
			}
		};
		function.apply(driver);
		return function.toString();
	}

	// @param tag = specified tag to search
	// @param attribute = filter by specified attribute
	// @param value = filter by specified attribute value
	// @return = list of all WebElements containing* specified filter
	public ArrayList<WebElement> getTagElementsContainingAttributeValue(String tag, String attribute, String value) {
		return getElements(By.cssSelector(tag + "[" + attribute + "*='" + value + "']"));
	}

	public ArrayList<WebElement> getTagElementsWithAttribute(String tag, String attribute) {
		return getElements(By.cssSelector(tag + "[" + attribute + "]"));
	}

	// @param tag = specified tag to search
	// @param attribute = filter by specified attribute
	// @param value = filter by specified attribute value
	// @return = list of all WebElements matching* specified filter
	public ArrayList<WebElement> getTagElementsWithAttributeValue(String tag, String attribute, String value) {
		return getElements(By.cssSelector(tag + "[" + attribute + "='" + value + "']"));
	}

	// retrieve text of WebElement located by byType
	public String getText(By by) {
		return getText(getElement(by));
	}

	// retrieve text of WebElement
	public String getText(WebElement we) {
		String text = null;
		try {
			text = we.getText();
			String details = "Retrieved text from element [" + webElementToString(we) + "]";
			logger.logMinorEvent(Utils.strsNotNull(text), details + "<br></u></strike><b>\"" + text + "\"</b>");
			logScreenshotElement(we);
		} catch (StaleElementReferenceException | NullPointerException e) {
			logger.logException(e);
		}
		return text;
	}

	// return list of text box WebElements
	public ArrayList<WebElement> getTextBoxes() {
		return getTagElementsWithAttribute("input", "maxlength");
	}

	// retrieve all texts from list derived from specified by-locator
	public String[] getTexts(By by) {
		return getTexts(getElements(by));
	}

	// retrieve all text from specified list
	public String[] getTexts(List<WebElement> elements) {
		int n = elements.size();
		String[] texts = new String[n];
		for (int i = 0; i < n; i++) {
			texts[i] = getText(elements.get(i));
		}
		return texts;
	}

	public void highlightElement(By by) {
		highlightElement(wait.forConditions(by, options.defaultWait.getValue(), "presence", "visible"));
	}

	public void highlightElement(WebElement we) {
		String color = Utils.randomItem("red", "green", "blue", "orange", "yellow", "purple", "cyan", "black");
		highlightElement(we, color);
	}

	public void highlightElement(WebElement we, String color) {
		String script = "arguments[0].style.border='3px solid %s'";
		jse.executeScript(String.format(script, color), we);
		logScreenshotElement(we);
	}

	public void highlightElements(By bys) {
		for (WebElement we : getElements(bys)) {
			highlightElement(we);
		}
	}

	public void highlightElements(By... bys) {
		String[] colors = { "red", "green", "blue", "orange", "yellow", "purple", "cyan", "black" };
		for (int i = 0; i < bys.length; i++) {
			try {
				highlightElement(getElement(bys[i]), colors[i % colors.length]);
			} catch (NullPointerException npe) {
				if (!options.continueOnException.getValue()) {
					throw npe;
				}
				logger.logException(npe);
			}
		}
	}

	public boolean isDisplayed(By by) {
		return isDisplayed(getElement(by));
	}

	public boolean isDisplayed(WebElement we) {
		return logger.logMinorEvent(we.isDisplayed(), "Element [" + webElementToString(we) + "] is displayed.");
	}

	public boolean isEnabled(By by) {
		return isEnabled(getElement(by));
	}

	public boolean isEnabled(WebElement we) {
		return logger.logMinorEvent(we.isEnabled(), "Element [" + webElementToString(we) + "] is enabled.");
	}

	public boolean isSelected(By by) {
		return isSelected(getElement(by));
	}

	public boolean isSelected(WebElement we) {
		return logger.logMinorEvent(we.isSelected(), "Element [" + webElementToString(we) + "] is selected.");
	}

	public boolean navigateTo(String url) {
		driver.get(url);
		String details = "Successfully navigated to url '" + url + "'";
		return logger.logMinorEvent(wait.forPageLoad(options.defaultWait.getValue()), details);
	}

	// print all driver logs
	public void printDriverLogs() {
		for (String logType : driver.manage().logs().getAvailableLogTypes()) {
			printDriverLogs(logType);
		}
	}

	// print all driver logs of a specific logType
	public void printDriverLogs(String logType) {
		Iterator<LogEntry> iterator = driver.manage().logs().get(logType).iterator();
		while (iterator.hasNext()) {
			logger.logInfo(iterator.next().getMessage());
		}
	}

	public File screenshotElement(WebElement we) {
		File file = null;
		if (we.isDisplayed()) {
			try {
				bot.moveToElement(we);
				String eleName = Utils.removeChars(webElementToString(we), "~!@#$&%^*':<>\\/()[]{}") + " - ";
				File screenshot = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
				Point p = we.getLocation();
				Dimension dim = we.getSize();
				BufferedImage fullImg = ImageIO.read(screenshot);
				BufferedImage eleScreenshot = fullImg.getSubimage(p.getX(), p.getY(), dim.getWidth(), dim.getHeight());
				ImageIO.write(eleScreenshot, "png", screenshot);
				file = new File(logger.getLoggerPath() + eleName + System.currentTimeMillis() + ".png");
				FileUtils.copyFile(screenshot, file);
			} catch (UnhandledAlertException uae) {
			} catch (RasterFormatException e) {
			} catch (Exception e) {
				logger.logException(e);
			}
		}
		return file;
	}

	public void logScreenshotElement(WebElement we) {
		File file = screenshotElement(we);
		String filename = file.getName();
		//bot.displayImage(file);
		logger.logInfo(logger.getTest().addScreenCapture(filename) + "<br>" + logger.colorTag("green", filename));
	}

	// select by byType
	public Select select(By by) {
		return select(wait.forConditions(by, options.defaultWait.getValue(), "visible", "enabled", "stale"));
	}

	// select by WebElement
	public Select select(WebElement we) {
		Select select = null;
		try {
			select = new Select(we);
		} catch (Exception e) {
			logger.logException(e);
		}
		logger.logMinorEvent(select != null, "Selected [" + webElementToString(we) + "]");
		logScreenshotElement(we);
		return select;
	}

	public boolean selectByIndex(By by, int index) {
		return selectByIndex(getElement(by), index);

	}

	// select by WebElement and select index option
	public boolean selectByIndex(WebElement we, int index) {
		boolean success = false;
		try {
			select(we).selectByIndex(index);
			success = true;
		} catch (Exception e) {
			logger.logException(e);
		}
		logger.logMinorEvent(success, "Selected index \"" + index + "\" from [" + webElementToString(we) + "]");
		logScreenshotElement(we);
		return success;
	}

	public boolean selectByRandomIndex(By by) {
		return selectByRandomIndex(
				wait.forConditions(by, options.defaultWait.getValue(), "visible", "enabled", "stale"));
	}

	public boolean selectByRandomIndex(WebElement we) {
		Select select = new Select(we);
		return select == null ? false : selectByIndex(we, select.getOptions().size() - 1);
	}

	// select visible option by ByType
	public boolean selectByVisibleText(By by, String visibleText) {
		return selectByVisibleText(getElement(by), visibleText);
	}

	// select by WebElement and select visible text
	public boolean selectByVisibleText(WebElement we, String visibleText) {
		boolean success = false;
		try {
			select(we).selectByVisibleText(visibleText);
			success = true;
		} catch (Exception e) {
			logger.logException(e);
		}
		logger.logMinorEvent(success, "Selected \"" + visibleText + "\" from [" + webElementToString(we) + "]");
		logScreenshotElement(we);
		return success;
	}

	// send keys by byType
	public boolean sendKeys(By by, CharSequence... keys) {
		return sendKeys(wait.forKeyable(by, options.defaultWait.getValue()), keys);
	}

	// send keys by WebElement
	public boolean sendKeys(WebElement we, CharSequence... keys) {
		StringBuilder keysVal = new StringBuilder();
		String elem = webElementToString(we);

		boolean success = false;
		try {
			try {
				we.clear();
				we.sendKeys(Keys.HOME);
			} catch (InvalidElementStateException iese) {
				// NOTE** some input elements cannot be cleared
				// this exception is caught when an unclearable element
				// invokes the .clear() method
				wait.forKeyable(toByVal(we), options.defaultWait.getValue());
			}
			we.sendKeys(keys);
			success = true;

			for (CharSequence cs : keys) {
				keysVal.append(cs);
			}
			logger.logMinorEvent(success, "Sent keys \"" + keysVal.toString() + "\" to [" + elem + "]");
			logScreenshotElement(we);
		} catch (Exception e) {
			if (!options.continueOnException.getValue()) {
				throw e;
			}
			logger.logException(e);
		}
		return success;
	}

	// switch to pop up and return parent window handler
	public String switchToPopup() {
		String parentWindow = driver.getWindowHandle(); // parent window
		String childWindow = driver.getWindowHandles().iterator().next();
		switchTo.window(childWindow);
		logger.logInfo("Switching to window: [" + driver.getTitle() + "]\n<br />\"" + driver.getCurrentUrl() + "\"");
		return parentWindow;
	}

	// return ByType of WebElement
	public By toByVal(WebElement we) {
		try {
			// By format = "[foundFrom] -> locator: term"
			// see RemoteWebElement toString() implementation
			String[] data = we.toString().split(" -> ")[1].replace("]", "").split(": ");
			String locator = data[0];
			String term = data[1];

			switch (locator) {
			case "xpath":
				return By.xpath(term);
			case "css selector":
				return By.cssSelector(term);
			case "id":
				return By.id(term);
			case "tag name":
				return By.tagName(term);
			case "name":
				return By.name(term);
			case "link text":
				return By.linkText(term);
			case "class name":
				return By.className(term);
			}
		} catch (Exception e) {
		}
		return By.class.cast(we);
	}

	// return true when pop up appears
	public boolean waitForPopup() {
		return logger.logMinorEvent(wait.forAlert(defaultWait), "Pop up found");
	}

	// return string representation of WebElement
	public String webElementToString(String we) {
		// By format = "[foundFrom] -> locator: term"
		// see RemoteWebElement toString() implementation
		return we.replaceAll("\\[.*?\\] -> ", "").replaceAll("]", "");
	}

	// return string representation of WebElement (handles links)
	public String webElementToString(WebElement we) {
		try {
			return webElementToString(we.toString());
		} catch (Exception e) {
			logger.logException(e);
			return null;
		}
	}
}
