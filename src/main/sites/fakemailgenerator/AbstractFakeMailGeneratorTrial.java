package main.sites.fakemailgenerator;

import main.sites.AbstractTrial;
import main.sites.fakemailgenerator.pages.LandingPage;

public abstract class AbstractFakeMailGeneratorTrial extends AbstractTrial {
	public final LandingPage landingPage = new LandingPage(this);

	public AbstractFakeMailGeneratorTrial(String url) {
		super(url);
	}

}
