package main.sites.smartystreets;

import main.sites.AbstractTrial;
import main.sites.smartystreets.pages.DemoPage;
import main.sites.smartystreets.pages.LandingPage;
import main.utils.metadata.ActorFactory;
import main.utils.metadata.ActorMetaData;

public abstract class AbstractSmartyStreetTrial extends AbstractTrial {
	protected final LandingPage landingPage = new LandingPage(this);
	protected final DemoPage demoPage = new DemoPage(this);
	protected final ActorMetaData actor;

	public AbstractSmartyStreetTrial(String url) {
		super(url);
		web.options.screenshotOnEvent.setValue(false);
		actor = ActorFactory.getLiveActor(web);
		web.options.screenshotOnEvent.setValue(true);
	}

	public AbstractSmartyStreetTrial() {
		this("https://smartystreets.com/");
	}

	@Override
	protected void setup() {
	}
}
