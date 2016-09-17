package main.sites.throwawaymail.trials;

import main.sites.throwawaymail.AbstractThrowAwayMailTrial;
import main.sites.twitter.pages.LandingPage;
import main.sites.twitter.pages.SignUpPage;
import main.utils.Utils;

public class TAMDemo0 extends AbstractThrowAwayMailTrial {
	LandingPage twitterLandingPage = new LandingPage(this);
	SignUpPage twitterSignUpPage = new SignUpPage(this);

	public TAMDemo0() {
		super("http://www.throwawaymail.com/");
	}

	@Override
	protected void test() {
		String twitter = "https://twitter.com/";

		// create email
		logger.logInfo(web.driver.getPageSource());
		String email = landingPage.getEmail();
		String username = email.replace("@", "");
		String fullname = Utils.getRandomString(5) + Utils.getRandomString(5);
		String password = email;

		// create twitter account
		web.navigateTo(twitter);
		twitterLandingPage.goToSignUpPage();
		twitterSignUpPage.signUp(fullname, email, password);

		// login
		web.navigateTo(twitter);
		twitterLandingPage.login(username, password);

		//
	}

}