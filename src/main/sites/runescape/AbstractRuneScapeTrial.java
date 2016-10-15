package main.sites.runescape;

import main.sites.AbstractTrial;
import main.sites.runescape.pages.LandingPage;

public abstract class AbstractRuneScapeTrial extends AbstractTrial {
	protected final LandingPage landingPage = new LandingPage(this);

	public AbstractRuneScapeTrial(String url) {
		super(url);
	}
}
