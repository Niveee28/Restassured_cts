package mypack;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Invoke {

	// ─── shared objects ──────────────────────────────────────────────────────────
	public static WebDriver driver;
	public static RespositoryParser parser;

	// ─── main ────────────────────────────────────────────────────────────────────
	public static void main(String[] args) throws Exception {

		Invoke invoke = new Invoke(); // create object
		invoke.setUpDriver(); // launch driver

		// read Excel data
		String[][] data = ExcelUtils.readExcelData("contacts.xlsx", "contacts_valid");

		// populate the form
		invoke.setNickName(data[0][0]);
		invoke.setContactName(data[0][1]);
		invoke.setCompany(data[0][2]);
		invoke.setCity(data[0][3]);
		invoke.setCountry(data[0][4]);
		invoke.setType(data[0][5]);

		invoke.clickAddButton(); // submit

		// Optionally print the result to console
		System.out.println("Nickname : " + invoke.getNickName());
		System.out.println("Contact  : " + invoke.getContactName());
		System.out.println("Company  : " + invoke.getCompany());
		System.out.println("City     : " + invoke.getCity());
		System.out.println("Country  : " + invoke.getCountry());
		System.out.println("Type     : " + invoke.getType());

		invoke.closeBrowser(); // close
	}

	// ─── driver / parser setup ───────────────────────────────────────────────────
	public void setUpDriver() throws Exception {
		driver = DriverSetup.getDriver();
		parser = new RespositoryParser("ObjectRepo.properties");
		driver.get("https://webapps.tekstac.com/AddressBook/");
	}

	// ─── setters ────────────────────────────────────────────────────────────────
	public void setNickName(String nickName) {
		WebElement el = driver.findElement(By.xpath(parser.getObjectLocator("nickname")));
		el.sendKeys(nickName);
	}

	public void setContactName(String contact) {
		WebElement el = driver.findElement(By.xpath(parser.getObjectLocator("contact")));
		el.sendKeys(contact);
	}

	public void setCompany(String company) {
		WebElement el = driver.findElement(By.xpath(parser.getObjectLocator("company")));
		el.sendKeys(company);
	}

	public void setCity(String city) {
		WebElement el = driver.findElement(By.xpath(parser.getObjectLocator("city")));
		el.sendKeys(city);
	}

	public void setCountry(String country) {
		WebElement el = driver.findElement(By.xpath(parser.getObjectLocator("country")));
		el.sendKeys(country);
	}

	public void setType(String type) {
		WebElement el = driver.findElement(By.xpath(parser.getObjectLocator("type")));
		el.sendKeys(type);
	}

	// ─── submit ─────────────────────────────────────────────────────────────────
	public void clickAddButton() {
		driver.findElement(By.xpath(parser.getObjectLocator("add"))).click();
	}

	// ─── getters (read text from #result div) ───────────────────────────────────
	private String extractField(String label) {
		String[] lines = driver.findElement(By.id("result")).getText().split("\n");
		for (String l : lines) {
			if (l.startsWith(label)) {
				return l.replace(label, "").trim();
			}
		}
		return "";
	}

	public String getNickName() {
		return extractField("NickName :");
	}

	public String getContactName() {
		return extractField("Contact Name :");
	}

	public String getCompany() {
		return extractField("Company :");
	}

	public String getCity() {
		return extractField("City :");
	}

	public String getCountry() {
		return extractField("Country :");
	}

	public String getType() {
		return extractField("Type :");
	}

	// ─── teardown ───────────────────────────────────────────────────────────────
	public void closeBrowser() {
		if (driver != null)
			driver.quit();
	}
}
