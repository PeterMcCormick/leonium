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
import main.sites.smartystreets.pages.DemoPage;
import main.utils.Utils;

public class BrowserHandler {

	public final WebDriver driver;
	public final JavascriptExecutor jse;
	public final BrowserReports reports;
	public final BrowserWait wait;
	public final BrowserBot bot;
	public final TargetLocator switchTo;
	public final BrowserHandlerOptions options;

	@SuppressWarnings("unused")
	private BrowserHandler() {
		this(null, null, -1);
	}

	public BrowserHandler(AbstractTrial validator) {
		this(validator, 15);
	}

	public BrowserHandler(AbstractTrial validator, int defaultWait) {
		this(validator.driver, validator.reports, defaultWait);
	}

	public BrowserHandler(WebDriver driver, BrowserReports reports) {
		this(driver, reports, 15);
	}

	public BrowserHandler(WebDriver driver, BrowserReports reports, int defaultWait) {
		this.driver = driver;
		this.reports = reports;
		this.switchTo = driver.switchTo();
		this.jse = ((JavascriptExecutor) driver);

		this.options = new BrowserHandlerOptions();
		this.wait = new BrowserWait(this);
		this.bot = new BrowserBot(this);

		options.defaultWait.setValue(defaultWait);
		options.screenshotOnClick.setValue(true);
		options.screenshotOnSendKeys.setValue(true);
		options.screenshotOnSelect.setValue(true);
		options.screenshotOnEvent.setValue(true);
		options.continueOnNoSuchElement.setValue(false);
		options.continueOnTimeout.setValue(false);
	}

	// click by byType
	public void click(By by) {
		click(wait.forConditions(by, options.defaultWait.getValue(), "presence", "visible", "enabled", "clickable",
				"stale"));
	}

	// click by WebElement
	public void click(WebElement we) {
		if (options.screenshotOnClick.getValue()) {
			screenshotElement(we);
		}
		we.click();
		reports.logInfo("Clicked [" + webElementToString(we) + "]");
	}

	// switch to and close pop up
	public void closePopup() {
		String parent = switchToPopup();
		reports.logInfo("Closing window: [" + driver.getCurrentUrl() + "]");
		switchTo.alert().dismiss();
		switchTo.window(parent);
	}

	// switch to and close all pop ups
	public void closePopups() {
		String parent = driver.getWindowHandle();
		Iterator<String> handles = driver.getWindowHandles().iterator();
		while (handles.hasNext()) {
			String next = handles.next();
			switchTo.window(next);
			Utils.print("Closing window: [" + driver.getTitle() + "]\n<br />[" + driver.getCurrentUrl() + "]");
			switchTo.window(next).close();
		}
		switchTo.window(parent);
	}

	// deletes all cached cookies and logs each deletion
	public void deleteAllCookies() {
		Options driverManager = driver.manage();
		reports.logInfo("Deleting all cookies");
		for (Cookie cookie : driverManager.getCookies()) {
			reports.logInfo("Deleting cookie \"" + cookie.getName() + "\"");
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
		return reports.logMinorEvent(outcome, "[" + webElementToString(we) + "] contained text '" + expectedText + "'");
	}

	public WebElement getActiveElement() {
		return switchTo.activeElement();
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
		reports.logMinorEvent(we != null, "Located element [" + webElementToString(we) + "]");
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
			elements[i] = getElement(bys[i]);
		}
		return elements;
	}

	// return list of WebElements with specified byType
	public ArrayList<WebElement> getElements(By by) {
		ArrayList<WebElement> elements = wait.forPresences(options.defaultWait.getValue(), by);
		int count = elements.size();
		reports.logMinorEvent(count > 0, "Located " + count + " elements [" + by + "]");
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
		text = we.getText();
		String details = "Retrieved text from element [" + webElementToString(we) + "]";
		reports.logMinorEvent(Utils.strsNotNull(text), details + "<br></u></strike><b>\"" + text + "\"</b>");
		screenshotElement(we);
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
		screenshotElement(we);
	}

	public void highlightElements(By bys) {
		for (WebElement we : getElements(bys)) {
			highlightElement(we);
		}
	}

	public void highlightElements(By... bys) {
		String[] colors = { "red", "green", "blue", "orange", "yellow", "purple", "cyan", "black" };
		for (int i = 0; i < bys.length; i++) {
			highlightElement(getElement(bys[i]), colors[i % colors.length]);
		}
	}

	public boolean isDisplayed(By by) {
		return isDisplayed(getElement(by));
	}

	public boolean isDisplayed(WebElement we) {
		return reports.logMinorEvent(we.isDisplayed(), "Element [" + webElementToString(we) + "] is displayed.");
	}

	public boolean isEnabled(By by) {
		return isEnabled(getElement(by));
	}

	public boolean isEnabled(WebElement we) {
		return reports.logMinorEvent(we.isEnabled(), "Element [" + webElementToString(we) + "] is enabled.");
	}

	public boolean isSelected(By by) {
		return isSelected(getElement(by));
	}

	public boolean isSelected(WebElement we) {
		return reports.logMinorEvent(we.isSelected(), "Element [" + webElementToString(we) + "] is selected.");
	}

	public boolean navigateTo(String url) {
		driver.get(url);
		String details = "Successfully navigated to url '" + url + "'";
		return reports.logMinorEvent(wait.forPageLoad(options.defaultWait.getValue()), details);
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
			reports.logInfo(iterator.next().getMessage());
		}
	}

	public void screenshotElement(By by) {
		screenshotElement(getElement(by));
	}

	public void screenshotElement(WebElement we) {
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
				file = new File(reports.getReportsPath() + eleName + System.currentTimeMillis() + ".png");
				FileUtils.copyFile(screenshot, file);
				reports.logInfo(reports.getTest().addScreenCapture(file.getName()) + "<br>"
						+ reports.colorTag("green", file.getName()));
			} catch (UnhandledAlertException uae) {
				bot.screenshotElement(we);
			} catch (RasterFormatException e) {
			} catch (Exception e) {
			}
		}
	}

	// select by byType
	public Select select(By by) {
		return select(wait.forConditions(by, options.defaultWait.getValue(), "visible", "enabled", "stale"));
	}

	// select by WebElement
	public Select select(WebElement we) {
		Select select = new Select(we);
		reports.logMinorEvent(select != null, "Selected [" + webElementToString(we) + "]");
		if (options.screenshotOnSelect.getValue()) {
			screenshotElement(we);
		}
		return select;
	}

	public void selectByIndex(By by, int index) {
		selectByIndex(getElement(by), index);
	}

	// select by WebElement and select index option
	public void selectByIndex(WebElement we, int index) {
		select(we).selectByIndex(index);
		reports.logMinorEvent(true, "Selected index \"" + index + "\" from [" + webElementToString(we) + "]");
		if (options.screenshotOnSelect.getValue()) {
			screenshotElement(we);
		}
	}

	public void selectByRandomIndex(By by) {
		selectByRandomIndex(wait.forConditions(by, options.defaultWait.getValue(), "visible", "enabled", "stale"));
	}

	public void selectByRandomIndex(WebElement we) {
		Select select = new Select(we);
		selectByIndex(we, select.getOptions().size() - 1);
	}

	// select visible option by ByType
	public void selectByVisibleText(By by, String visibleText) {
		selectByVisibleText(getElement(by), visibleText);
	}

	// select by WebElement and select visible text
	public void selectByVisibleText(WebElement we, String visibleText) {
		select(we).selectByVisibleText(visibleText);
		reports.logMinorEvent(true, "Selected \"" + visibleText + "\" from [" + webElementToString(we) + "]");
		if (options.screenshotOnSelect.getValue()) {
			screenshotElement(we);
		}
	}

	// send keys by byType
	public void sendKeys(By by, CharSequence... keys) {
		sendKeys(wait.forKeyable(by, options.defaultWait.getValue()), keys);
	}

	// send keys by WebElement
	public void sendKeys(WebElement we, CharSequence... keys) {
		if (keys == null || !Utils.strsNotNull(Utils.toString(keys))) {
			return;
		}
		String elem = webElementToString(we);
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

		reports.logInfo("Sent keys \"" + Utils.toString(keys) + "\" to [" + elem + "]");
		if (options.screenshotOnClick.getValue()) {
			screenshotElement(we);
		}
	}

	// switch to pop up and return parent window handler
	public String switchToPopup() {
		String parentWindow = driver.getWindowHandle(); // parent window
		String childWindow = driver.getWindowHandles().iterator().next();
		switchTo.window(childWindow);
		reports.logInfo("Switching to window: [" + driver.getTitle() + "]\n<br />\"" + driver.getCurrentUrl() + "\"");
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
		return reports.logMinorEvent(wait.forAlert(options.defaultWait.getValue()), "Pop up found");
	}

	// return string representation of WebElement
	public String webElementToString(String we) {
		// By format = "[foundFrom] -> locator: term"
		// see RemoteWebElement toString() implementation
		return we.replaceAll("\\[.*?\\] -> ", "").replaceAll("]", "");
	}

	// return string representation of WebElement (handles links)
	public String webElementToString(WebElement we) {
		return webElementToString(we.toString());
	}
}
