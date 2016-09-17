package main.sites.twitter;

import main.sites.AbstractTrial;
import main.sites.twitter.pages.LandingPage;

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
