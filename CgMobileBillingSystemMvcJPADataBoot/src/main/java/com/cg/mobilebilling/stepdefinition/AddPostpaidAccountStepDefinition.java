package com.cg.mobilebilling.stepdefinition;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.cg.mobilbilling.pagebeans.AddPostpaidAccountPage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AddPostpaidAccountStepDefinition {
	private WebDriver driver;
	private AddPostpaidAccountPage page;
	@Given("^User is on Add Postpaid Account Page$")
	public void user_is_on_Add_Postpaid_Account_Page() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:4444/addPostpaidAccount");
		page=PageFactory.initElements(driver,AddPostpaidAccountPage.class);
	}

	@When("^Enters valid 'Customer ID'$")
	public void enters_valid_Customer_ID() throws Throwable {
		page.setCustomerID("1001");
	}

	@When("^Selects plan$")
	public void selects_plan() throws Throwable {
		page.setPlanID("101");
		page.clickSubmit();
	}

	@Then("^New Postpaid Account is generated$")
	public void new_Postpaid_Account_is_generated() throws Throwable {
		String expectedTitle = "Post-paid Account Page";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(expectedTitle, actualTitle);
		driver.close();
	}

	@When("^Enters invalid 'Customer ID'$")
	public void enters_invalid_Customer_ID() throws Throwable {
		page.setCustomerID("999999");
	}

	@Then("^Error message displayed showing invalid 'Customer ID'$")
	public void error_message_displayed_showing_invalid_Customer_ID() throws Throwable {
		String expectedError = "Customer not found for Customer ID: " + "999999";
		String actualError = page.getErrorMessage();
		Assert.assertEquals(expectedError, actualError);
		driver.close();
	}
}
