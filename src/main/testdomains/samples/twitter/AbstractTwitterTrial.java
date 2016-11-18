package main.testdomains.samples.twitter;

import main.testdomains.samples.AbstractTrial;
import main.testdomains.samples.twitter.pages.LandingPage;

public abstract class AbstractTwitterTrial extends AbstractTrial {
	protected LandingPage landingPage = new LandingPage(this);

	public AbstractTwitterTrial(String url) {
		super(url);
	}

	@Override
	protected void setup() {

	}

	protected abstract void test();
}
