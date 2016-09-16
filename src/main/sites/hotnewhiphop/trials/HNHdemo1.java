package main.sites.hotnewhiphop.trials;

import org.openqa.selenium.WebDriver;

import main.sites.hotnewhiphop.AbstractHNHipHopTrial;

public class HNHdemo1 extends AbstractHNHipHopTrial {
	public HNHdemo1() {
		super("http://www.hotnewhiphop.com/");
	}

	@Override
	protected void setup() {
	}

	@Override
	protected void test() {
		web.click(homePage.byButtonMoreSongs);
		web.getTexts(homePage.bySongChart);
	}
}