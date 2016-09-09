package main;

import org.openqa.selenium.firefox.FirefoxDriver;

import main.sites.AbstractTrial;
import main.sites.friendproject.trials.FPDemo1;
import main.sites.hotnewhiphop.trials.HNHdemo1;
import main.sites.instagram.trials.IGTdemo1;
import main.sites.mail.trials.MailDemo1;
import main.sites.mail.trials.MailDemo2;
import main.sites.msn.trials.MsnDemo1;
import main.sites.twitter.trials.TwitterDemo0;
import main.utils.Utils;

public class MainDriver {

	public static void main(String[] args) {
		try {
			// twitterTest();
			mailTest2b();
			// fpTest();
			// hnhhTest();
			// mailTest1();
			// msnTest();
			// igTest();
		} catch (Exception e) {
			Utils.generalException(e);
		}
	}

	public static void fpTest() {
		new FPDemo1("http://www.friendproject.net/").run();
	}

	public static void hnhhTest() {
		new HNHdemo1("http://www.hotnewhiphop.com/").run();
	}

	public static void igTest() {
		new IGTdemo1("https://www.instagram.com/").run();
	}

	public static void msnTest() {
		new MsnDemo1("http://www.msn.com/").run();
	}

	public static void twitterTest() {
		new TwitterDemo0("https://twitter.com/").run();
	}

	public static void mailTest2a() {
		new MailDemo2("http://mail.com/").run();
	}

	public static void mailTest2b() {
		String url = "https://service.mail.com/registration.html?edition=us&lang=en&#.7518-header-signup2-1";
		AbstractTrial t = null;
		for (int i = 0; i < 1; i++) {
			t = new MailDemo2(url);
			t.start();
		}
		while (t.isAlive()) {
		}
		Utils.openFile(t.getLoggerPath() + "/Result.html");
	}

	public static void mailTest1() {
		new MailDemo1(new FirefoxDriver(), "https://www.google.com").run();
	}
}
