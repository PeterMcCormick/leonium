package main.sites;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
		ArrayList<By> byList = Utils.getFieldValues(this, By.class);
		int n = byList.size();
		By[] byArray = new By[n];
		for (int i = 0; i < n; i++) {
			byArray[i] = byList.get(i);
		}
		return byArray;
	}

	public void highlightElements() {
		boolean defaultOption = web.options.continueOnException.isEnabled();
		web.options.continueOnException.setEnabled(true);
		web.highlightElements(getDeclaredBys());
		web.options.continueOnException.setEnabled(defaultOption);
	}
}
