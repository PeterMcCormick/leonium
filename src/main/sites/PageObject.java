package main.sites;

import org.openqa.selenium.By;

import main.utils.Utils;
import main.utils.browserutils.BrowserHandler;

public abstract class PageObject {
	protected AbstractTrial runner;
	protected BrowserHandler web;

	public PageObject(AbstractTrial runner) {
		this.runner = runner;
		this.web = runner.web;
	}

	public By[] getDeclaredBys() {
		return Utils.toArray(Utils.getFieldValues(this, By.class));
	}

	public void highlightElements() {
		boolean defaultOption = web.options.continueOnNoSuchElement.getValue();
		web.options.continueOnNoSuchElement.setValue(true);
		web.highlightElements(getDeclaredBys());
		web.options.continueOnNoSuchElement.setValue(defaultOption);
	}
}
