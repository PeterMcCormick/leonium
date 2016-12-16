package main.utils.browserutils.browserreporting;

import com.relevantcodes.extentreports.ExtentReports;

public class BrowserReportsManager {
	private static ExtentReports extent;

	public synchronized static ExtentReports getReporter(String filePath) {
		if (extent == null) {
			extent = new ExtentReports(filePath, true);

			extent.addSystemInfo("Host Name", "Anshoo").addSystemInfo("Environment", "QA");
		}

		return extent;
	}

	public synchronized static ExtentReports getReporter() {
		return extent;
	}
}