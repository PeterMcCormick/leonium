package main.utils.browserutils.browserreporting;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import main.sites.AbstractTrial;
import main.utils.Utils;

public class BrowserReports {
	private TakesScreenshot browserCam;
	private final ExtentTest extentTest;
	private static String reportsPath;

	public BrowserReports(String reportsPath, Class<? extends AbstractTrial> trial, WebDriver driver) {
		BrowserReports.reportsPath = reportsPath;
		Utils.createMissingDirectories(reportsPath);
		BrowserReportsManager.getReporter(reportsPath + "Result.html");
		this.extentTest = BrowserTestManager.startTest(getTrialName(trial));
		this.browserCam = (TakesScreenshot) new Augmenter().augment(driver);
	}

	public String colorTag(String color, String text) {
		return "<font color = \"" + color + "\">" + text + "</font>";
	}

	// flushes test to HTML report
	public void endTest() {
		BrowserTestManager.endTest();
		BrowserReportsManager.getReporter(reportsPath).flush();
	}

	public String getReportsPath() {
		return reportsPath;
	}

	public ExtentTest getTest() {
		return extentTest;
	}

	public String getTestName() {
		return extentTest.getTest().getName();
	}

	private String getTrialName(Class<? extends AbstractTrial> trial) {
		return Utils.removeChars(trial.getSimpleName(), "!@#$%^&*()_+:{}");
	}

	private String klickable(StackTraceElement ste) {
		String className = ste.getClassName();
		String methodName = ste.getMethodName();
		className = className.substring(className.lastIndexOf('.') + 1);
		methodName = Utils.strsNotNull(methodName) ? methodName : "constructor";

		return "(" + className + ".java:" + ste.getLineNumber() + ")." + methodName;
	}

	// @return = boolean success
	// @optional parameter: testName
	public boolean reportCriticalEvent(boolean success) {
		return reportCriticalEvent(success, getTestName());
	}

	// @return = boolean success
	// @optional parameter: event description
	// wrapper method for reportCriticalEvent(success, pass, fail);
	public boolean reportCriticalEvent(boolean success, String event) {
		return reportCriticalEvent(success, event, event);
	}

	// @return = boolean success
	// reports outcome of critical-event
	// the outcome of this method call determines the success of the test
	public boolean reportCriticalEvent(boolean success, String pass, String fail) {
		if (success) {
			extentTest.log(LogStatus.PASS, colorTag("blue", pass));
		} else {
			extentTest.log(LogStatus.FAIL, colorTag("red", "<strike>" + fail + "</strike>"));
		}
		Utils.printR(removeTags(pass.replace("<br>", "\n\t")) + " = " + success);
		return success;
	}

	// Specifically log data-retrieval & data-capture information
	public String reportDataRetrieval(String description) {
		return reportInfo(colorTag("purple", description));
	}

	// Add log dialogue to report corresponding to specified exception
	public String reportStackTrace(Exception e) {
		StringBuilder result = new StringBuilder();
		StackTraceElement ste = Utils.lastMethodCall(3);
		String exceptionName = colorTag("red", e.getClass().getSimpleName());
		String header = exceptionName + " caught by " + klickable(ste);
		String tmessage = e.getMessage() == null ? "Exception Message = null" : e.getMessage();
		String[] details = tmessage.split("\n");
		String delimiter = "<br>";
		String delimiter2 = delimiter + delimiter;
		String message = details[0];
		String info = String.join("\n", Arrays.copyOfRange(details, 1, details.length)).replace("\n", "<br>");

		result.append(message + delimiter2 + info + delimiter2);
		result.append("<b><u>Stacktrace:</b></u>");

		StackTraceElement[] stes = e.getStackTrace();
		Collections.reverse(Arrays.asList(stes));
		for (int i = 1; i < stes.length; i++) {
			StackTraceElement sti = stes[i];
			if (sti.getLineNumber() < 0) {
				continue;
			}
			result.append(delimiter + ">> " + klickable(sti));
		}

		return reportInfo("<b><u>" + header + "<br></b></u>" + result.toString());
	}

	// Log multiple details delimited by line break
	public String reportInfo(String baseString, Object... args) {
		String info = String.format(baseString, args);
		try {
			Utils.printR("\n\nLogging information...");
			Utils.printR(removeTags(info.replace("<br>", "\n\t")));
			info = Utils.removeStrings(info, " = false", " = true");
			extentTest.log(LogStatus.INFO, info);
		} catch (Exception e) {
		}
		return info;
	}

	public boolean reportMinorEvent(boolean outcome, String passMessage) {
		String failMessage = colorTag("red", "<strike>" + passMessage + "</strike>") + " = " + outcome;
		return reportMinorEvent(outcome, passMessage, failMessage);
	}

	// log outcome of non-critical-event
	public boolean reportMinorEvent(boolean outcome, String passMessage, String failMessage) {
		if (outcome) {
			String description = colorTag("blue", passMessage);
			Utils.printR(removeTags(description.replace("<br>", "\n\t")));
			description = description.replace(" = false", "").replace(" = true", "");
			extentTest.log(LogStatus.PASS, description);
		} else {
			reportInfo(colorTag("red", failMessage));
		}
		return outcome;
	}

	// remove HTML tags from specified string
	public String removeTags(String html) {
		return html.replaceAll("\\<[^>]*>", "");
	}

	// Create screenshot file
	private File screenshot(String fileName) {
		try {
			fileName = Utils.removeChars(fileName, "~!@#$&%^*':<>\\/()[]{}") + "_" + System.currentTimeMillis();
			File screenshot = browserCam.getScreenshotAs(OutputType.FILE);
			new File(reportsPath).mkdirs();
			File f = new File(Utils.printR(reportsPath + "\\" + fileName + ".png"));
			FileUtils.copyFile(screenshot, f);
			return f;
		} catch (Exception e) {
			reportStackTrace(e);
		}
		return null;
	}

	public void screenshotPage() {
		screenshotPage(getTestName());
	}

	public void screenshotPage(String testDescription) {
		try {
			reportInfo(extentTest.addScreenCapture(screenshot(testDescription).getName()) + testDescription);
		} catch (Exception e) {
			reportStackTrace(e);
		}
	}

	public boolean reportTextContains(String dynamicVal, String expectedVal) {
		String passMessage = "'" + dynamicVal + "' contains '" + expectedVal + "'";
		String failMessage = "'" + dynamicVal + "' does not contain '" + expectedVal + "'";
		return reportMinorEvent(expectedVal.contains(dynamicVal), passMessage, failMessage);
	}

	public boolean reportTextEquals(String dynamicVal, String expectedVal) {
		String passMessage = "'" + dynamicVal + "' matches '" + expectedVal + "'";
		String failMessage = "'" + dynamicVal + "' does not match '" + expectedVal + "'";
		return reportMinorEvent(expectedVal.equals(dynamicVal), passMessage, failMessage);
	}
}