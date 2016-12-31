package main.utils.browserutils.browserreporting;

import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class BrowserTestManager { // new
	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
	private static ExtentReports extent = BrowserReportsManager.getReporter();

	public static synchronized ExtentTest getTest() {
		return extentTestMap.get(getThreadId());
	}

	public static synchronized void endTest() {
		extent.endTest(extentTestMap.get(getThreadId()));
	}

	public static synchronized ExtentTest startTest(String testName) {
		return startTest(testName, "");
	}

	public static synchronized ExtentTest startTest(String testName, String desc) {
		ExtentTest test = extent.startTest(testName, desc);
		extentTestMap.put(getThreadId(), test);
		return test;
	}

	private static synchronized int getThreadId() {
		return (int) Thread.currentThread().getId();
	}
}