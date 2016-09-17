package main.sites.thesaurus;

import main.sites.AbstractTrial;
import main.sites.thesaurus.pages.LandingPage;

public abstract class AbstractThesaurusTrial extends AbstractTrial {
	public final LandingPage landingPage = new LandingPage(this);

	public AbstractThesaurusTrial(String url) {
		super(url);
	}

}
