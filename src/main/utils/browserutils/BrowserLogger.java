package main.utils.browserutils;

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

public class BrowserLogger extends ExtentReports {
	private final TakesScreenshot browserCam;
	private final ExtentTest extentTest;
	private final String loggerPath;

	public BrowserLogger(String loggerPath, Class<? extends AbstractTrial> trial, WebDriver driver) {
		super(loggerPath + "/Result.html", false);
		this.loggerPath = loggerPath;
		this.extentTest = startTest(getTrialName(trial));
		this.browserCam = (TakesScreenshot) new Augmenter().augment(driver);
		Utils.makeMissingDirectories(loggerPath);
	}

	public String colorTag(String color, String text) {
		return "<font color = \"" + color + "\">" + text + "</font>";
	}

	public synchronized void endTest() {
		try {
			this.flush();
			this.endTest(extentTest);
		} catch (Exception e) {
			Utils.generalException(e);
		}
	}

	public String getLoggerPath() {
		return loggerPath;
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
	public boolean logCriticalEvent(boolean success) {
		String testName = extentTest.getTest().getName();
		return logCriticalEvent(success, testName, testName);
	}

	public boolean logCriticalEvent(boolean success, String pass, String fail) {
		if (success) {
			extentTest.log(LogStatus.PASS, colorTag("blue", pass));
		} else {
			extentTest.log(LogStatus.FAIL, colorTag("red", "<strike>" + fail + "</strike>"));
		}
		Utils.print(removeTags(pass.replace("<br>", "\n\t")) + " = " + success);
		return success;
	}

	// Specifically log data-retrieval & data-capture information
	public String logDataRetrieval(String description) {
		return logInfo(colorTag("purple", description));
	}

	// Add log dialogue to report corresponding to specified exception
	public String logException(Exception e) {
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

		return logInfo("<b><u>" + header + "<br></b></u>" + result.toString());
	}

	// Log multiple details delimited by line break
	public String logInfo(String baseString, Object... args) {
		String info = String.format(baseString, args);
		try {
			Utils.print("\n\nLogging information...");
			Utils.print(removeTags(info.replace("<br>", "\n\t")));
			info = Utils.removeStrings(info, " = false", " = true");
			extentTest.log(LogStatus.INFO, info);
		} catch (Exception e) {
		}
		return info;
	}

	public boolean logMinorEvent(boolean outcome, String passMessage) {
		String failMessage = colorTag("red", "<strike>" + passMessage + "</strike>") + " = " + outcome;
		return logMinorEvent(outcome, passMessage, failMessage);
	}

	// log outcome of non-critical-event
	public boolean logMinorEvent(boolean outcome, String passMessage, String failMessage) {
		if (outcome) {
			String description = colorTag("blue", passMessage);
			Utils.print(removeTags(description.replace("<br>", "\n\t")));
			description = description.replace(" = false", "").replace(" = true", "");
			extentTest.log(LogStatus.PASS, description);
		} else {
			logInfo(colorTag("red", failMessage));
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
			new File(loggerPath).mkdirs();
			File f = new File(Utils.print(loggerPath + "\\" + fileName + ".png"));
			FileUtils.copyFile(screenshot, f);
			return f;
		} catch (Exception e) {
			logException(e);
		}
		return null;
	}

	public void screenshotPage() {
		screenshotPage(getTestName());
	}

	public void screenshotPage(String testDescription) {
		try {
			logInfo(extentTest.addScreenCapture(screenshot(testDescription).getName()) + testDescription);
		} catch (Exception e) {
			logException(e);
		}
	}

	public boolean textContains(String dynamicVal, String expectedVal) {
		String passMessage = "'" + dynamicVal + "' contains '" + expectedVal + "'";
		String failMessage = "'" + dynamicVal + "' does not contain '" + expectedVal + "'";
		return logMinorEvent(expectedVal.contains(dynamicVal), passMessage, failMessage);
	}

	public boolean textEquals(String dynamicVal, String expectedVal) {
		String passMessage = "'" + dynamicVal + "' matches '" + expectedVal + "'";
		String failMessage = "'" + dynamicVal + "' does not match '" + expectedVal + "'";
		return logMinorEvent(expectedVal.equals(dynamicVal), passMessage, failMessage);
	}
}