package main.sites.mail.pages;

import org.openqa.selenium.By;

import main.sites.PageObject;
import main.sites.AbstractTrial;
import main.utils.Utils;
import main.utils.browserutils.BrowserLogger;

public class SignUpPage extends PageObject {
	public By byInputFirstName = By.xpath("(.//input)[2]");
	public By byInputLastName = By.xpath("(.//input)[3]");
	public By byInputDesiredEmail = By.xpath("(.//input)[4]");
	public By byInputPassword = By.xpath("(.//input)[5]");
	public By byInputConfirmPassword = By.xpath("(.//input)[6]");
	public By byInputContactEmail = By.xpath("(.//input)[7]");
	public By byInputSecurityAnswer = By.xpath("(.//input)[9]");
	public By byButtonCreateAccount = By.xpath("(.//input)[11]");

	public By bySelectGender = By.xpath("(.//select)[1]");
	public By bySelectDobMonth = By.cssSelector("(.//select)[2]");
	public By bySelectDobDay = By.xpath("(.//select)[3]");
	public By bySelectDobYear = By.xpath("(.//select)[4]");
	public By bySelectCountry = By.xpath("(.//select)[5]");
	public By bySelectSecurityQuestion = By.xpath("(.//select)[7]");

	public By byCheckBoxRecaptcha = By.xpath("[class='recaptcha-checkbox-checkmark']");

	public SignUpPage(AbstractTrial runner) {
		super(runner);
	}

	public void initSelectors() {
		String inputPath = "(.//input)[%s]";
		int offset1 = 0, offset2 = -1;
		try {
			web.wait.forVisibility(xp(inputPath, 2), 5);
		} catch (Exception e) {
			offset1 = 1;
			offset2 = 0;
		}

		this.byInputFirstName = xp(inputPath, 2 + offset1);
		this.byInputLastName = xp(inputPath, 3 + offset1);
		this.byInputDesiredEmail = xp(inputPath, 5 + offset1);
		this.byInputPassword = xp(inputPath, 6 + offset1);
		this.byInputConfirmPassword = xp(inputPath, 7 + offset1);

		Utils.printFields(this);

	}

	public By xp(String query, Object... args) {
		return By.xpath(String.format(query, args));
	}

	public void signUp(String firstName, String lastName, String gender, String birthMonth, String birthDay,
			String birthYear, String country, String desiredEmail, String password, String contactEmail,
			String securityQuestion, String securityAnswer) {

		web.sendKeys(byInputLastName, lastName);
		web.selectByVisibleText(bySelectGender, gender);
		web.selectByVisibleText(bySelectDobMonth, birthMonth);
		web.selectByVisibleText(bySelectDobDay, birthDay);
		web.selectByVisibleText(bySelectDobYear, birthYear);
		web.selectByVisibleText(bySelectCountry, country);
		web.sendKeys(byInputDesiredEmail, desiredEmail);
		web.sendKeys(byInputPassword, password);
		web.sendKeys(byInputConfirmPassword, password);
		web.sendKeys(byInputContactEmail, contactEmail);
		web.selectByVisibleText(bySelectSecurityQuestion, securityQuestion);
		web.sendKeys(byInputSecurityAnswer, securityAnswer);
		web.click(byButtonCreateAccount);
	}

	public void signUp(String firstName, String lastName, String desiredEmail, String password) {
		BrowserLogger bl = web.logger;
		initSelectors();

		bl.logInfo("First name1");
		web.sendKeys(byInputFirstName, firstName);

		bl.logInfo("Last name");
		web.sendKeys(byInputLastName, lastName);

		bl.logInfo("Desired email");
		web.sendKeys(byInputDesiredEmail, desiredEmail);

		bl.logInfo("Password");
		web.sendKeys(byInputPassword, password);

		bl.logInfo("Confirm password");
		web.sendKeys(byInputConfirmPassword, password);

		bl.logInfo("Gender");
		web.selectByRandomIndex(bySelectGender);

		bl.logInfo("Month");
		web.selectByRandomIndex(web.getElements(By.cssSelector("select")).get(1));

		bl.logInfo("Year");
		web.selectByVisibleText(bySelectDobYear, "19" + Utils.randint(50, 90));

		bl.logInfo("Day");
		web.selectByRandomIndex(bySelectDobDay);

		bl.logInfo("Country");
		web.selectByRandomIndex(bySelectCountry);

		bl.logInfo("Security question");
		web.selectByRandomIndex(bySelectSecurityQuestion);

		bl.logInfo("Security answer");
		web.sendKeys(byInputSecurityAnswer, "securityAnswer");

		bl.logInfo("Create account");
		web.click(byButtonCreateAccount);

	}
}