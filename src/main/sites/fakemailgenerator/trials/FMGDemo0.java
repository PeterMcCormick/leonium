package main.sites.fakemailgenerator.trials;

import main.sites.fakemailgenerator.AbstractFakeMailGeneratorTrial;
import main.sites.twitter.pages.AddPhonePage;
import main.sites.twitter.pages.AddUsernamePage;
import main.sites.twitter.pages.LandingPage;
import main.sites.twitter.pages.SignUpPage;
import main.utils.Utils;

public class FMGDemo0 extends AbstractFakeMailGeneratorTrial {
	LandingPage twitterLandingPage = new LandingPage(this);
	SignUpPage twitterSignUpPage = new SignUpPage(this);
	AddPhonePage twitterAddPhonePage = new AddPhonePage(this);
	AddUsernamePage twitterAddUsernamePage = new AddUsernamePage(this);

	public FMGDemo0() {
		super("http://www.fakemailgenerator.com/");
	}

	@Override
	protected void test() {
		String twitter = "https://twitter.com/";

		// create email
		String email = landingPage.getMyEmail();
		String firstname = Utils.getRandomString(5);
		String lastname = Utils.getRandomString(5);
		String fullname = firstname + " " + lastname;

		String username = Utils.removeChars(email, ".").replace("@", ".");
		String password = Utils.randomCasing(Utils.reverseString(email));

		// signUpPage
		web.navigateTo(twitter);
		twitterLandingPage.goToSignUpPage();
		twitterSignUpPage.signUp(fullname, email, password);
		monitor("twitterSignUpPage.signUp();");

		// addPhonePage
		twitterAddPhonePage.clickSkip();
		monitor("twitterAddPhonePage.clickSkip();");

		// addUsernamePage
		twitterAddUsernamePage.clickSuggestion();
		monitor("twitterAddUsernamePage.clickSuggestion();");

		// login
		web.navigateTo(twitter);
		twitterLandingPage.login(username, password);
		monitor("twitterLandingPage.login();");

		//
	}

	private void monitor(String description) {
		try {
			web.wait.forPageLoad();
		} catch (Exception e) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		logger.screenshotPage(description);
		try {
			// logger.logInfo(driver.getPageSource());
		} catch (Exception e) {
		}
	}

	@Override
	protected void setup() {
	}

}
