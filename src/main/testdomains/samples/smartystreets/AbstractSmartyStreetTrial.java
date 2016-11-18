package main.testdomains.samples.smartystreets;

import main.testdomains.samples.AbstractTrial;
import main.testdomains.samples.smartystreets.pages.DemoPage;
import main.testdomains.samples.smartystreets.pages.LandingPage;
import main.utils.metadata.ActorFactory;
import main.utils.metadata.ActorMetaData;

public abstract class AbstractSmartyStreetTrial extends AbstractTrial {
	protected final LandingPage landingPage = new LandingPage(this);
	protected final DemoPage demoPage = new DemoPage(this);
	protected final ActorMetaData actor = ActorFactory.getLiveActor(web);

	public AbstractSmartyStreetTrial(String url) {
		super(url);
	}

	public AbstractSmartyStreetTrial() {
		super("https://smartystreets.com/");
	}

	@Override
	protected void setup() {
	}
}
