package main.sites.twitter.trials;

import main.sites.twitter.AbstractTwitterTrial;

public class TwitterDemo0 extends AbstractTwitterTrial {

	public TwitterDemo0() {
		super("https://twitter.com/");
	}

	@Override
	protected void test() {
		landingPage.login("username", "password");
		web.wait.forPageLoad();
		web.click(landingPage.byButtonLogin0);
	}
}
