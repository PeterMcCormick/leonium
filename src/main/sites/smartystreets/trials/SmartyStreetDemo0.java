package main.sites.smartystreets.trials;

import main.sites.addressgenerator.trials.AddressGeneratorDemo0;
import main.sites.smartystreets.AbstractSmartyStreetTrial;
import main.sites.smartystreets.pages.DemoPage;
import main.utils.Utils;

public class SmartyStreetDemo0 extends AbstractSmartyStreetTrial {

	public SmartyStreetDemo0() {
		super("https://smartystreets.com/demo");
	}

	@Override
	protected void setup() {
		web.options.screenshotOnEvent.setValue(false);
		web.options.defaultWait.setValue(50);
	}

	protected void test() {
		web.options.screenshotOnEvent.setValue(true);
		web.navigateTo(url);
		demoPage.selectService("us");
		demoPage.enterDetails(actor.getAddressLine1(), actor.getCity(), actor.getState(), actor.getZipcode());
		web.click(DemoPage.USView.byButtonSubmit);
		web.wait.forPageLoad();
		web.screenshotElement(DemoPage.USView.bySpanMatchCount);
		logger.screenshotPage();
	}
}