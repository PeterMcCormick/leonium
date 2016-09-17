package main.sites;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import main.utils.Utils;
import main.utils.browserutils.BrowserHandler;
import main.utils.browserutils.BrowserLogger;
import main.utils.browserutils.browserwrappers.PhantomDriver;

public abstract class AbstractTrial extends Thread {

	protected boolean pass;
	protected String initialUrl;

	public final RemoteWebDriver remoteDriver;
	public final WebDriver driver;
	public final BrowserLogger logger;
	public final BrowserHandler web;

	protected static final String date = Utils.getDate();
	protected static final String home = System.getProperty("user.home");
	protected static final String name = System.getProperty("user.name");

	public AbstractTrial(String url) {
		this(new PhantomDriver(), url);
	}

	public AbstractTrial(WebDriver driver, String url) {
		this.pass = false;
		this.initialUrl = url;
		this.driver = driver;
		this.remoteDriver = (RemoteWebDriver) driver;
		this.logger = new BrowserLogger(getLoggerPath(), getClass(), driver);
		this.web = new BrowserHandler(this);
		web.deleteAllCookies();
	}

	public void run() {
		try {
			web.navigateTo(initialUrl);
			setup();
			test();
		} catch (Exception e) {
			logger.logException(e);
		} finally {
			tearDown();
		}
	}

	protected abstract void setup();

	protected abstract void test();

	public void tearDown() {
		try {
			logger.logInfo("Tearing down test...");
			logger.endTest();
			Utils.openFile(getLoggerPath() + "/Result.html");
			driver.quit();
		} catch (Exception e) {
			Utils.generalException(e);
		}
	}

	public String getLoggerPath() {
		Class<? extends AbstractTrial> cls = getClass();
		return getLoggerPath("MyTrials", cls.getSuperclass().getSimpleName());
	}

	private String getLoggerPath(String root, String... paths) {

		StringBuilder subRoot = new StringBuilder();
		for (String path : paths) {
			subRoot.append("/" + path);
		}
		return String.format("%S/%s/%s/%s-%s/", home, root, subRoot.toString(), date, name);
	}
}