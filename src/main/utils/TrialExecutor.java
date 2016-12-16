package main.utils;

import java.util.concurrent.atomic.AtomicInteger;

import main.sites.AbstractTrial;

public class TrialExecutor {
	static AtomicInteger runningTests = new AtomicInteger();
	private final int maxThreadCount;

	public TrialExecutor(int maxThreadCount) {
		this.maxThreadCount = maxThreadCount;
	}

	public void executeTrials(AbstractTrial... trials) {
		for (AbstractTrial trial : trials) {
			while (runningTests.get() >= maxThreadCount) {
				Utils.wait(1);
			}
			startNewTest(trial);
		}
	}

	public synchronized <T extends AbstractTrial> AbstractTrial startNewTest(T trial) {
		runningTests.incrementAndGet();
		new Thread() {
			public void run() {
				trial.run();
			}
		}.start();
		return trial;
	}
}
