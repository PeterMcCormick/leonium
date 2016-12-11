package main.utils;

import main.sites.AbstractTrial;
import main.sites.smartystreets.trials.SmartyStreetDemo0;

public class TrialFactory {

	private static void start(AbstractTrial... trials) {
		for (AbstractTrial trial : trials)
			new Thread() {
				public void run() {
					trial.run();
				}
			}.start();
	}

	public static void smartyStreetTest() {
		start(new SmartyStreetDemo0());
	}
}
