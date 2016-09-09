package main.sites.twitter.trials;

import main.sites.twitter.AbstractTwitterTrial;

public class TwitterDemo0 extends AbstractTwitterTrial {

	public TwitterDemo0(String url) {
		super(url);
	}

	@Override
	protected void test() {		
		landingPage.login("username", "password");
		web.wait.forPageLoad();
		logger.screenshotPage();
		web.click(landingPage.byButtonLogin0);
	}
}
