package main.testdomains.samples.addressgenerator;

import main.testdomains.samples.AbstractTrial;
import main.testdomains.samples.addressgenerator.pages.LandingPage;
import main.utils.browserutils.BrowserHandler;

public abstract class AbstractAddressGeneratorTrial extends AbstractTrial {
	protected final LandingPage landingPage = new LandingPage(this);

	public AbstractAddressGeneratorTrial(BrowserHandler web) {
		super(web, "https://names.igopaygo.com/street/north-american-address");
	}

	public AbstractAddressGeneratorTrial() {
		super("https://names.igopaygo.com/street/north-american-address");
	}

	@Override
	protected void setup() {
	}
}
