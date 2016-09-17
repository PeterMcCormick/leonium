package main.sites.thesaurus.trials;

import main.sites.thesaurus.AbstractThesaurusTrial;

public class ThesaurusDemo0 extends AbstractThesaurusTrial {

	public ThesaurusDemo0() {
		super("http://www.thesaurus.com/");
	}

	@Override
	protected void setup() {

	}

	@Override
	protected void test() {
		landingPage.search("hello");
		logger.screenshotPage();
	}

}
