package main.sites.addressgenerator.trials;

import main.sites.addressgenerator.AbstractAddressGeneratorTrial;
import main.utils.browserutils.BrowserHandler;

public class AddressGeneratorDemo0 extends AbstractAddressGeneratorTrial {
	private String[] addresses;

	public AddressGeneratorDemo0() {
	}

	public AddressGeneratorDemo0(BrowserHandler web) {
		super(web);
	}

	@Override
	protected void test() {
		landingPage.enterDetails();
		addresses = web.getTexts(landingPage.byDivAddresses);
	}

	public String[] getAddresses() {
		return addresses;
	}
}
