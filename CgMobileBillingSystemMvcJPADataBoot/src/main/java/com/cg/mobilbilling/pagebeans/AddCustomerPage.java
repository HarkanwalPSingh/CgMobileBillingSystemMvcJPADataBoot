package com.cg.mobilbilling.pagebeans;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AddCustomerPage {
	@FindBy(how=How.CLASS_NAME,name="firstName")
	private WebElement  firstName;
	@FindBy(how=How.CLASS_NAME,name="lastName")
	private WebElement  lastName;
	@FindBy(how=How.CLASS_NAME,name="emailID")
	private WebElement  emailID;
	@FindBy(how=How.CLASS_NAME,name="dateOfBirth")
	private WebElement  dateOfBirth;
	@FindBy(how=How.CLASS_NAME,name="billingAddress.pinCode")
	private WebElement  pinCode;
	@FindBy(how=How.CLASS_NAME,name="billingAddress.city")
	private WebElement  city;
	@FindBy(how=How.CLASS_NAME,name="billingAddress.state")
	private WebElement  state;
	@FindBy(how=How.CLASS_NAME,name="submit")
	private WebElement button;
	@FindBy(how=How.ID,id="firstNameError")
	private WebElement  firstNameError;

	public void clickSubmit() {
		this.button.click();
	}

	public String getFirstNameError() {
		return firstNameError.getText();
	}

	public void setFirstName(String firstName) {
		this.firstName.clear();
		this.firstName.sendKeys(firstName);
	}

	public void setLastName(String lastName) {
		this.lastName.clear();
		this.lastName.sendKeys(lastName);
	}

	public void setEmailID(String emailID) {
		this.emailID.clear();
		this.emailID.sendKeys(emailID);
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth.clear();
		this.dateOfBirth.sendKeys(dateOfBirth);
	}

	public void setPinCode(String pinCode) {
		this.pinCode.clear();
		this.pinCode.sendKeys(pinCode);
	}

	public void setCity(String city) {
		this.city.clear();
		this.city.sendKeys(city);
	}

	public void setState(String state) {
		this.state.clear();
		this.state.sendKeys(state);
	}
}
