package main.sites.hotnewhiphop;

import main.sites.AbstractTrial;
import main.sites.hotnewhiphop.pages.HomePage;

public abstract class AbstractHNHipHopTrial extends AbstractTrial {

	protected final HomePage homePage = new HomePage(this);

	public AbstractHNHipHopTrial() {
		super("http://www.hotnewhiphop.com/");

	}
}
