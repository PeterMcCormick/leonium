 package main.sites;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import main.utils.Utils;
import main.utils.browserutils.BrowserHandler;
import main.utils.browserutils.BrowserReports;
import main.utils.browserutils.browserwrappers.PhantomBrowser;

public abstract class AbstractTrial extends Thread {

	protected boolean pass;
	protected String url;

	public final WebDriver driver;
	public final BrowserReports reports;
	public final BrowserHandler web;

	protected static final String date = Utils.getDate();
	protected static final String home = System.getProperty("user.home");
	protected static final String name = System.getProperty("user.name");

	public AbstractTrial(AbstractTrial trial, String url) {
		this(trial.driver, url);
	}

	public AbstractTrial(String url) {
		this(new PhantomBrowser(), url);
	}

	public AbstractTrial(WebDriver driver, String url) {
		this.pass = false;
		this.url = url;
		this.driver = driver;
		this.reports = new BrowserReports(getLoggerPath(), getClass(), driver);
		this.web = new BrowserHandler(driver, reports, 15);
	}

	public AbstractTrial(WebDriver driver, BrowserReports logger, String url) {
		this.pass = false;
		this.url = url;
		this.driver = driver;
		this.reports = logger;
		this.web = new BrowserHandler(driver, logger, 15);
	}

	public AbstractTrial(BrowserHandler web, String url) {
		this(web.driver, web.reports, url);
	}

	public void run() {
		try {
			web.navigateTo(url);
			setup();
			test();
			pass = true;
		} catch (Exception e) {
			e.printStackTrace();
			reports.reportStackTrace(e);
		} finally {
			tearDown();
		}
	}

	protected abstract void setup();

	protected abstract void test();

	public void tearDown() {
		try {
			reports.reportInfo("Tearing down test...");
			reports.reportCriticalEvent(pass);
			reports.endTest();
			Utils.openFile(getLoggerPath() + "Result.html");
			driver.quit();
		} catch (Exception e) {
			Utils.printStackTrace(e);
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