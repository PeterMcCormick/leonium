package main.sites.twitter;

import main.sites.AbstractTrial;
import main.sites.twitter.pages.LandingPage;

public abstract class TwitterTrial extends AbstractTrial {
	protected LandingPage landingPage = new LandingPage(this);

	public TwitterTrial(String url) {
		super(url);
	}

	@Override
	protected void setup() {

	}

	protected abstract void test();
}
