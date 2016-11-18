package main.testdomains.samples.runescape;

import main.testdomains.samples.AbstractTrial;
import main.testdomains.samples.runescape.pages.LandingPage;

public abstract class AbstractRuneScapeTrial extends AbstractTrial {
	protected final LandingPage landingPage = new LandingPage(this);

	public AbstractRuneScapeTrial(String url) {
		super(url);
	}
}
