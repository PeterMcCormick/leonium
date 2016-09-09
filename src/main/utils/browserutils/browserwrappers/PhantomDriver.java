package main.utils.browserutils.browserwrappers;

import java.util.ArrayList;
import java.util.logging.Level;

import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import main.utils.Utils;

public class PhantomDriver extends PhantomJSDriver {
	static {
		String userAgent = "Mozilla/5.0 (Windows NT 6.0) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1";
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "info");
		System.setProperty("org.openqa.selenium.remote.RemoteWebDriver", "info");
		System.setProperty("phantomjs.page.settings.userAgent", userAgent);
	}

	public PhantomDriver(DesiredCapabilities caps) {
		super(DesiredCapabilities.phantomjs().merge(caps));
	}

	public PhantomDriver() {
		super(desiredCapabilities());
		this.setLogLevel(Level.OFF);
		getErrorHandler().setIncludeServerErrors(false);
	}

	private static DesiredCapabilities desiredCapabilities() {
		DesiredCapabilities caps = new DesiredCapabilities();
		ArrayList<String> cliArgsCap = new ArrayList<String>();
		cliArgsCap.add("--webdriver-loglevel=NONE");

		caps.setJavascriptEnabled(true);
		caps.setCapability("takesScreenshot", true);
		caps.setCapability("screen-resolution", "1280x1024");
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "./resources/phantomjs.exe");
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
		Utils.disableLogging(PhantomJSDriverService.class, RemoteWebDriver.class, Augmenter.class);
		return DesiredCapabilities.phantomjs().merge(caps);
	}
}