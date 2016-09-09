package main.sites.twitter.trials;

import main.sites.twitter.TwitterTrial;

public class TwitterDemo0 extends TwitterTrial {

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
