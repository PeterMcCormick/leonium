package main.testdomains.samples.addressgenerator.pages;

import org.openqa.selenium.By;

import main.testdomains.samples.AbstractTrial;
import main.testdomains.samples.PageObject;
import main.utils.Utils;
import main.utils.browserutils.With;

public class LandingPage extends PageObject {
	public final By bySelectHowMany = By.name("how_many");
	public final By byButtonCreate = By.id("create");
	public final By bySelectRegionalAddress = By.name("country");
	public final By bySelectShortCodes = By.name("short_codes");
	public final By bySelectRealCities = By.name("real_cities");
	public final By bySelectLocationData = By.name("lat_lon");
	public final By bySelectLocationDataCalc = By.name("lat_lon_calc");
	public final By byDivAddresses = By.xpath("((.//*[@class='mysql'])[1])/ul/li");

	// With.classValueHaving("country");
	// By.cssSelector("select[name='country']");
	// With.classValueHaving("shortCodes");
	// By.cssSelector("select[name='shortCodes']");
	// With.classValueHaving("real-cities");
	// By.cssSelector("select[name='real-cities']");
	// With.classValueBeing("lat_lon");
	// By.cssSelector("select[name='lat_lon']");
	// By.cssSelector("select[name='lat_lon_calc']");

	public LandingPage(AbstractTrial runner) {
		super(runner);
	}

	public void enterDetails() {
		enterDetails(50, "United States", false, true, false, false);
	}

	public void enterDetails(Integer amount, String regionalAddress, boolean shortCodes, boolean realCities,
			boolean locationData, boolean locationCalculation) {
		web.selectByVisibleText(bySelectHowMany, amount.toString());
		web.selectByVisibleText(bySelectRegionalAddress, regionalAddress);
		web.selectByVisibleText(bySelectShortCodes, Utils.affirmate(shortCodes));
		web.selectByVisibleText(bySelectRealCities, Utils.affirmate(realCities));
		web.selectByVisibleText(bySelectLocationData, Utils.affirmate(locationData));
		web.selectByVisibleText(bySelectLocationDataCalc, Utils.affirmate(shortCodes));
		web.click(byButtonCreate);
		web.wait.forPageLoad();
	}

}
