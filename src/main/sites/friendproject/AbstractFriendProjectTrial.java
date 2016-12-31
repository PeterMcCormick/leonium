package main.sites.friendproject;

import main.sites.AbstractTrial;
import main.sites.friendproject.pages.HomePage;

public abstract class AbstractFriendProjectTrial extends AbstractTrial {
	protected final HomePage homePage = new HomePage(this);

	public AbstractFriendProjectTrial(String url) {
		super(url);
	}

	public AbstractFriendProjectTrial() {
		super("http://www.friendproject.net/");
	}

	protected abstract void setup();

	protected abstract void test();
}
