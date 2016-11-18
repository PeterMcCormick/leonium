package main.testdomains.samples.runescape.trials;

import org.openqa.selenium.WebElement;

import main.testdomains.samples.runescape.AbstractRuneScapeTrial;

public class RuneScapeDemo0 extends AbstractRuneScapeTrial {

	public RuneScapeDemo0() {
		super("http://www.runescape.com/");
	}

	@Override
	protected void setup() {
	}

	@Override
	protected void test() {
		WebElement whatIs = web.getElements(landingPage.byHeaderLinks).get(0);
		web.click(whatIs);
	}
}
