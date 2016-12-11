package main.utils.browserutils.browserwrappers;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FirefoxBrowser extends FirefoxDriver {
	static {
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "info");
		System.setProperty("org.openqa.selenium.remote.RemoteWebDriver", "info");
	}

	public FirefoxBrowser() {
		this(defaultCapabilities());
	}

	public FirefoxBrowser(DesiredCapabilities caps) {
		super(caps);
	}

	private static DesiredCapabilities defaultCapabilities() {
		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setJavascriptEnabled(true);
		caps.setCapability("takesScreenshot", true);
		caps.setCapability("screen-resolution", "1280x1024");
		caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		caps.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
		// caps.setCapability(FirefoxDriver.PROFILE, defaultProfile());
		return caps;
	}

	private static FirefoxProfile defaultProfile() {
		return createProfile(false, false, false, false);
	}

	private static FirefoxProfile createProfile(boolean acceptUntrustedSsl, boolean loadNoFocusLib,
			boolean untrustedIssuer, boolean enableNativeEvents) {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(acceptUntrustedSsl);
		profile.setAlwaysLoadNoFocusLib(loadNoFocusLib);
		profile.setAssumeUntrustedCertificateIssuer(untrustedIssuer);
		profile.setEnableNativeEvents(enableNativeEvents);
		return profile;
	}

}