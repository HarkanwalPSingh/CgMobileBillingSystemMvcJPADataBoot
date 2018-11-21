package com.cg.mobilebilling.stepdefinition;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.cg.mobilbilling.pagebeans.CustomerDetailsPage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CustomerDetailsStepDefinition {
	private WebDriver driver;
	private CustomerDetailsPage page;
	@Given("^User is on Get Customer Details Page$")
	public void user_is_on_Get_Customer_Details_Page() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "D:\\\\chromedriver_win32\\\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:4444/customerDetails");
		page=PageFactory.initElements(driver,CustomerDetailsPage.class);
	}

	@When("^User enters valid Customer ID$")
	public void user_enters_valid_Customer_ID() throws Throwable {
		page.setCustomerID("1001");
	}

	@When("^Clicks Submit button$")
	public void clicks_Submit_button() throws Throwable {
		page.clickSubmit();
	}

	@Then("^Customer Details Displayed on Customer Details Page$")
	public void customer_Details_Displayed_on_Customer_Details_Page() throws Throwable {
		String expectedTitle = "Customer Details";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(expectedTitle, actualTitle);
		driver.close();
	}

	@When("^User enters invalid Customer ID$")
	public void user_enters_invalid_Customer_ID() throws Throwable {
		page.setCustomerID("7889");
	}

	@Then("^Error message is displayed showing invalid Customer ID$")
	public void error_message_is_displayed_showing_invalid_Customer_ID() throws Throwable {
		String expectedError = "Customer not found for Customer ID: " + "7889";
		String actualError = page.getErrorMessage();
		Assert.assertEquals(expectedError, actualError);
		driver.close();
	}
}
