package main.sites.hotnewhiphop.trials;

import org.openqa.selenium.WebDriver;

import main.sites.hotnewhiphop.HNHipHopTrial;

public class HNHdemo1 extends HNHipHopTrial {
	public HNHdemo1(String url) {
		super(url);
	}

	public HNHdemo1(WebDriver driver, String url) {
		super(driver, url);
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