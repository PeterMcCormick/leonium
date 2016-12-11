package main.sites.smartystreets.trials;

import main.sites.smartystreets.AbstractSmartyStreetTrial;
import main.sites.smartystreets.pages.DemoPage;

public class SmartyStreetDemo0 extends AbstractSmartyStreetTrial {

	public SmartyStreetDemo0() {
		super("https://smartystreets.com/demo");
	}

	@Override
	protected void setup() {
		web.options.screenshotOnEvent.setValue(false);
		web.options.defaultWait.setValue(60);
	}

	protected void test() {
		web.options.screenshotOnEvent.setValue(true);
		web.navigateTo(url);
		demoPage.selectService("us");
		demoPage.enterDetails(actor.getAddressLine1(), actor.getCity(), actor.getState(), actor.getZipcode());
		web.click(DemoPage.USView.byButtonSubmit);
		web.options.defaultWait.setValue(60);
		web.wait.forPageLoad();
		web.screenshotElement(DemoPage.USView.bySpanMatchCount);
		reports.screenshotPage();
	}
}