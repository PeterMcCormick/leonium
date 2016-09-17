package main.utils.browserutils.browserwrappers;

import java.util.ArrayList;
import java.util.logging.Level;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
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

	private PhantomDriver(DesiredCapabilities caps) {
		super(DesiredCapabilities.phantomjs().merge(caps));
		this.setLogLevel(Level.OFF);
		this.getErrorHandler().setIncludeServerErrors(false);
		this.manage().window().maximize();
	}

	public PhantomDriver() {
		this(desiredCapabilities());
	}

	public PhantomDriver(String proxyDomain, String username, String password) {
		this(proxyCapabilities(proxyDomain, username, password));

	}

	private static DesiredCapabilities desiredCapabilities() {
		DesiredCapabilities caps = new DesiredCapabilities();
		ArrayList<String> cliArgsCap = new ArrayList<String>();
		cliArgsCap.add("--webdriver-loglevel=NONE");

		caps.setJavascriptEnabled(true);
		caps.setCapability("takesScreenshot", true);
		caps.setCapability("screen-resolution", "1280x1024");
		caps.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
		caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		caps.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, false);
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "./resources/phantomjs.exe");
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
		Utils.disableLogging(PhantomJSDriverService.class, RemoteWebDriver.class, Augmenter.class);
		return DesiredCapabilities.phantomjs().merge(caps);
	}

	private static DesiredCapabilities proxyCapabilities(String proxyDomain, String username, String password) {
		ArrayList<String> cliArgsCap = new ArrayList<String>();
		DesiredCapabilities caps = desiredCapabilities();

		cliArgsCap.add("--webdriver-loglevel=NONE");
		cliArgsCap.add("--proxy=" + proxyDomain);
		cliArgsCap.add(String.format("--proxy-auth=%s:%s", username, password));
		cliArgsCap.add("--proxy-type=socks5");
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);

		caps.setCapability(CapabilityType.PROXY, getProxy(proxyDomain));
		return caps;
	}

	private static Proxy getProxy(String proxyUrl) {
		return new Proxy().setHttpProxy(proxyUrl).setFtpProxy(proxyUrl).setSslProxy(proxyUrl);
	}

}