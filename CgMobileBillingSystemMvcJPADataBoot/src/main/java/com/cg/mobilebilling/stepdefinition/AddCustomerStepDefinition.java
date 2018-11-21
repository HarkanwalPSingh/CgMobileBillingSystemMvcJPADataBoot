package com.cg.mobilebilling.stepdefinition;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import com.cg.mobilbilling.pagebeans.AddCustomerPage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AddCustomerStepDefinition {
	private WebDriver driver;
	private AddCustomerPage page;
	@Given("^User is on Add Customer Page$")
	public void user_is_on_Add_Customer_Page() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:4444/registerCustomer");
		page=PageFactory.initElements(driver,AddCustomerPage.class);
	}
	//For testing individual fields create when for each field
	//Here only one field is checked
	@When("^Enters correct input values$")
	public void enters_correct_input_values() throws Throwable {
		page.setFirstName("Sahil");
		page.setLastName("Singh");
		page.setEmailID("hkps@gmail.com");
		page.setDateOfBirth("13/12/1993");
		page.setPinCode("141002");
		page.setCity("Ludhiana");
		page.setState("Punjab");
	}

	@When("^Click submit button$")
	public void click_submit_button() throws Throwable {
		page.clickSubmit();
	}

	@Then("^New Customer will be added to database$")
	public void new_Customer_will_be_added_to_database() throws Throwable {
		String expectedTitle = "Customer Successfully Added";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(expectedTitle, actualTitle);
		driver.close();
	}

	@When("^Enters incorrect input values$")
	public void enters_incorrect_input_values() throws Throwable {
		page.setFirstName("");
		page.setLastName("Singh");
		page.setEmailID("hkps@gmail.com");
		page.setDateOfBirth("13/12/1993");
		page.setPinCode("141002");
		page.setCity("Ludhiana");
		page.setState("Punjab");
	}

	@Then("^Error message is shown corresponding to incorrect values entered$")
	public void error_message_is_shown_corresponding_to_incorrect_values_entered() throws Throwable {
		String expectedError = "Enter First Name";
		String actualError = page.getFirstNameError();
		Assert.assertEquals(expectedError, actualError);
		driver.close();
	}
}
