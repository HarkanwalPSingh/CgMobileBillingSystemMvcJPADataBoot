package com.cg.mobilbilling.pagebeans;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RemoveCustomerPage {
	@FindBy(how = How.CLASS_NAME, name = "customerID")
	private WebElement customerID;
	@FindBy(how = How.CLASS_NAME, name = "submit")
	private WebElement button;
	@FindBy(how = How.ID, id = "errorMessage")
	private WebElement errorMessage;

	public RemoveCustomerPage() {
	}

	public void setCustomerID(String customerID) {
		this.customerID.sendKeys(customerID);
	}

	public void clickSubmit() {
		this.button.click();
	}

	public String getErrorMessage() {
		return this.errorMessage.getText();
	}

}
