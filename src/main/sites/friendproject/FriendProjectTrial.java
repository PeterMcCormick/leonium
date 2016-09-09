package main.sites.friendproject;

import main.sites.AbstractTrial;
import main.sites.friendproject.pages.HomePage;

public abstract class FriendProjectTrial extends AbstractTrial {
	protected final HomePage homePage = new HomePage(this);

	public FriendProjectTrial(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	protected abstract void setup();

	protected abstract void test();
}
