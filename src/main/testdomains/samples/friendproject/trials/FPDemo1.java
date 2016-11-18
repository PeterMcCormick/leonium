package main.testdomains.samples.friendproject.trials;

import main.testdomains.samples.friendproject.AbstractFriendProjectTrial;

public class FPDemo1 extends AbstractFriendProjectTrial {

	public FPDemo1(String url) {
		super(url);
	}

	@Override
	protected void setup() {
	}

	@Override
	protected void test() {
		homePage.login("HelloWorld@pobox.com@", "password");
		homePage.signUp("FirstName", "LastName", "Email@pobox.com", "password");
	}

}
