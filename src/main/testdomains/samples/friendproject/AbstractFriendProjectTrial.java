package main.testdomains.samples.friendproject;

import main.testdomains.samples.AbstractTrial;
import main.testdomains.samples.friendproject.pages.HomePage;

public abstract class AbstractFriendProjectTrial extends AbstractTrial {
	protected final HomePage homePage = new HomePage(this);

	public AbstractFriendProjectTrial(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	protected abstract void setup();

	protected abstract void test();
}
