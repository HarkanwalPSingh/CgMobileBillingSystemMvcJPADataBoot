package com.cg.mobilebilling.stepdefinition;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.cg.mobilbilling.pagebeans.RemoveCustomerPage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RemoveCustomerStepDefinition {
	private WebDriver driver;
	private RemoveCustomerPage page;
	@Given("^User is on Remove Customer Page$")
	public void user_is_on_Remove_Customer_Page() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "D:\\\\chromedriver_win32\\\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:4444/removeCustomer");
		page=PageFactory.initElements(driver,RemoveCustomerPage.class);
	}

	@When("^Enters valid Customer ID$")
	public void enters_valid_Customer_ID() throws Throwable {
		page.setCustomerID("1002");
	}

	@When("^Clicks Submit Button$")
	public void clicks_Submit_Button() throws Throwable {
		page.clickSubmit();
	}

	@Then("^Customer gets removed from database and customer removed success page is displayed$")
	public void customer_gets_removed_from_database_and_customer_removed_success_page_is_displayed() throws Throwable {
		String expectedTitle = "Customer Successfully Removed";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(expectedTitle, actualTitle);
		driver.close();
	}

	@When("^Enters invalid Customer ID$")
	public void enters_invalid_Customer_ID() throws Throwable {
		page.setCustomerID("4444442");
	}

	@Then("^Invalid Customer ID message displayed on Remove Customer Page$")
	public void invalid_Customer_ID_message_displayed_on_Remove_Customer_Page() throws Throwable {
		
		String expectedError = "Customer not found for Customer ID: " + "4444442";
		String actualError = page.getErrorMessage();
		Assert.assertEquals(expectedError, actualError);
		driver.close();
	}
}
