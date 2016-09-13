package main.sites.throwawaymail.trials;

import main.sites.throwawaymail.AbstractThrowAwayMailTrial;
import main.sites.twitter.pages.LandingPage;
import main.sites.twitter.pages.SignUpPage;

public class TAMDemo0 extends AbstractThrowAwayMailTrial {
	LandingPage twitterLandingPage = new LandingPage(this);
	SignUpPage twitterSignUpPage = new SignUpPage(this);

	public TAMDemo0() {
		super("https://twitter.com/");
	}

	@Override
	protected void test() {
		// create email
		String username = null;
		String password = null;
		String fullname = null;
		String email = null;

		// create twitter account
		twitterSignUpPage.signUp(fullname, email, password);

		// login
		twitterLandingPage.login(username, password);

		//
	}

}