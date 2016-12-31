package main.sites.friendproject.trials;

import main.sites.friendproject.AbstractFriendProjectTrial;

public class FPDemo1 extends AbstractFriendProjectTrial {

	public FPDemo1( ) {
		super();
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
