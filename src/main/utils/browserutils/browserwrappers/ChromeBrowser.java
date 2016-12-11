package main.utils.browserutils.browserwrappers;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ChromeBrowser extends ChromeDriver {
	private static String executablePath = "./resources/chromedriver.exe";

	static {
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "info");
		System.setProperty("org.openqa.selenium.remote.RemoteWebDriver", "info");
		System.setProperty("webdriver.chrome.driver", executablePath);
	}

	public ChromeBrowser() {
		this(defaultCapabilities());
	}

	public ChromeBrowser(DesiredCapabilities caps) {
		super(caps);
	}

	private static DesiredCapabilities defaultCapabilities() {
		ChromeOptions options = new ChromeOptions();
		DesiredCapabilities caps = new DesiredCapabilities();

		options.addArguments("start-maximized");

		caps.setJavascriptEnabled(true);
		caps.setCapability("takesScreenshot", true);
		caps.setCapability("screen-resolution", "1280x1024");
		caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		caps.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
		caps.setCapability(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, executablePath);
		return caps;

	}

}