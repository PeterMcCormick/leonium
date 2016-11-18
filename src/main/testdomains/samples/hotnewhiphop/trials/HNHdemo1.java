package main.testdomains.samples.hotnewhiphop.trials;

import main.testdomains.samples.hotnewhiphop.AbstractHNHipHopTrial;

public class HNHdemo1 extends AbstractHNHipHopTrial {
	public HNHdemo1() {
		super();
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