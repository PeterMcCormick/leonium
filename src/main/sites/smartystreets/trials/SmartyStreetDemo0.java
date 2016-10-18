package main.sites.smartystreets.trials;

import main.sites.smartystreets.AbstractSmartyStreetTrial;
import main.sites.smartystreets.pages.DemoPage;

public class SmartyStreetDemo0 extends AbstractSmartyStreetTrial {
	public SmartyStreetDemo0() {
		super("https://smartystreets.com/demo");
	}

	protected void test() {
		demoPage.selectService("us");
		demoPage.enterDetails(actor.getAddressLine1(), actor.getCity(), actor.getState(), actor.getZipcode());
		web.click(DemoPage.USView.byButtonSubmit);
		web.wait.forPageLoad();
		web.screenshotElement(DemoPage.USView.bySpanMatchCount);
		logger.screenshotPage();
	}
}