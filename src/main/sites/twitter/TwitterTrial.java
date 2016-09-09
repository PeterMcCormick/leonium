package main.sites.twitter;

import main.sites.Trial;
import main.sites.twitter.pages.LandingPage;

public abstract class TwitterTrial extends Trial {
	protected LandingPage landingPage = new LandingPage(this);

	public TwitterTrial(String url) {
		super(url);
	}

	@Override
	protected void setup() {

	}

	protected abstract void test();
}
