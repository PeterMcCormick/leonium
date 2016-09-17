package main.sites.throwawaymail;

import main.sites.AbstractTrial;
import main.sites.throwawaymail.pages.LandingPage;

public abstract class AbstractThrowAwayMailTrial extends AbstractTrial {
	public final LandingPage landingPage = new LandingPage(this);

	public AbstractThrowAwayMailTrial(String url) {
		super(url);
	}

	@Override
	protected void setup() {
	}

}
