package main.sites.mail.trials;

import org.openqa.selenium.WebDriver;

import main.sites.mail.AbstractMailTrial;
import main.utils.Utils;

public class MailDemo2 extends AbstractMailTrial {
	private Long seed = System.nanoTime();
	private String firstName = Utils.getColumnVal(seed);
	private String lastName = Utils.getColumnVal(seed.hashCode() + this.hashCode());
	private String desiredEmail = String.format("%s.%s_%s@email.com", firstName, lastName, seed);

	public MailDemo2(String url) {
		super(url);
	}

	public MailDemo2(WebDriver driver, String url) {
		super(driver, url);
	}

	protected void setup() {
		web.options.continueOnException.setEnabled(true);
		web.setDefaultWait(5);
	}

	@Override
	protected void test() {
		try {
			signUpPage.highlightElements();
			signUpPage.signUp(firstName, lastName, desiredEmail, "Password0!");

			pass = true;
		} catch (Exception e) {
			logger.logException(e);
		} finally {
			logger.screenshotPage();
		}
	}

}
