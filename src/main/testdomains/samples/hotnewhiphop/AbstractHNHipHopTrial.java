package main.testdomains.samples.hotnewhiphop;

import main.testdomains.samples.AbstractTrial;
import main.testdomains.samples.hotnewhiphop.pages.HomePage;

public abstract class AbstractHNHipHopTrial extends AbstractTrial {

	protected final HomePage homePage = new HomePage(this);

	public AbstractHNHipHopTrial() {
		super("http://www.hotnewhiphop.com/");

	}
}
