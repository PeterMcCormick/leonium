package main.testdomains.samples.addressgenerator.trials;

import main.testdomains.samples.addressgenerator.AbstractAddressGeneratorTrial;
import main.utils.browserutils.BrowserHandler;

public class AddressGeneratorDemo0 extends AbstractAddressGeneratorTrial {

	public AddressGeneratorDemo0() {
	}

	public AddressGeneratorDemo0(BrowserHandler web) {
		super(web);
	}

	private String[] addresses;

	protected void test() {
		landingPage.enterDetails();
		addresses = web.getTexts(landingPage.byDivAddresses);
	}

	public void tearDown() {
	}

	public String[] getAddresses() {
		return addresses;
	}
}
