package com.cg.mobilbilling.pagebeans;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


public class AddPostpaidAccountPage {
	@FindBy(how=How.CLASS_NAME,name="customerID")
	private WebElement customerID;
	@FindBy(how=How.CLASS_NAME,name="planID")
	private List<WebElement> planIDs;
	@FindBy(how=How.CLASS_NAME,name="submit")
	private WebElement button;
	@FindBy(how=How.ID,id="error")
	private WebElement errorMessage;
	
	public AddPostpaidAccountPage() {}
	

	public void clickSubmit() {
		this.button.click();
	}
	public String getErrorMessage() {
		return errorMessage.getText();
	}
	public void setCustomerID(String customerID) {
		this.customerID.sendKeys(customerID);
	}
	public void setPlanID(String planID) {
		for(WebElement plan : this.planIDs) 
			if(plan.getAttribute("value").equals(planID))
				plan.click();
	}
	
}
