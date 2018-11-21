package com.cg.mobilebilling.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cg.mobilebilling.beans.PostpaidAccount;
import com.cg.mobilebilling.exceptions.BillingServicesDownException;
import com.cg.mobilebilling.exceptions.CustomerDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.PlanDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.PostpaidAccountNotFoundException;
import com.cg.mobilebilling.services.BillingServices;

@Controller
public class PostpaidAccountController {
	private PostpaidAccount postpaidAccount;
	private List<PostpaidAccount> postpaidAccounts;
	@Autowired
	private BillingServices billingServices;

	@RequestMapping("registerPostpaidAccount")
	public ModelAndView registerPostpaidAccountAction(@RequestParam("customerID") int customerID,
			@RequestParam("planID") int planID){
		try {
			postpaidAccount = billingServices.openPostpaidMobileAccount(customerID, planID);
			return new ModelAndView("/addPostpaidAccountSuccessPage", "postpaidAccount", postpaidAccount);
		} catch (PlanDetailsNotFoundException | CustomerDetailsNotFoundException | BillingServicesDownException e) {
			return new ModelAndView("/addPostpaidAccountPage", "error", e.getMessage());
		}
		
	}

	@RequestMapping("getPostpaidAccountDetails")
	public ModelAndView getPostpaidAccountDetailsAction(@RequestParam("customerID") int customerID,
			@RequestParam("mobileNo") long mobileNo){
		try {
			postpaidAccount = billingServices.getPostPaidAccountDetails(customerID, mobileNo);
			return new ModelAndView("/postpaidAccountDisplayDetailsPage", "postpaidAccount", postpaidAccount);
		} catch (CustomerDetailsNotFoundException | PostpaidAccountNotFoundException | BillingServicesDownException e) {
			return new ModelAndView("/postpaidAccountDetailsPage", "error", e.getMessage());
		}
		
	}
	@RequestMapping("closeCustomerPostpaidAccount")
	public ModelAndView closeCustomerPostpaidAccountAction(@RequestParam("customerID") int customerID,
			@RequestParam("mobileNo") long mobileNo){
		try {
			billingServices.closeCustomerPostPaidAccount(customerID, mobileNo);
			return new ModelAndView("/removePostpaidAccountSuccessPage", "mobileNo", mobileNo);
		} catch (CustomerDetailsNotFoundException | PostpaidAccountNotFoundException | BillingServicesDownException e) {
			return new ModelAndView("/removePostpaidAccountPage", "error", e.getMessage());
		}
		
	}
	@RequestMapping("getAllPostpaidAccountDetails")
	public ModelAndView getAllPostpaidAccountDetailsAction(@RequestParam("customerID") int customerID){
		try {
			postpaidAccounts = billingServices.getCustomerAllPostpaidAccountsDetails(customerID);
			return new ModelAndView("/allPostpaidAccountDisplayDetailsPage", "postpaidAccounts", postpaidAccounts);
		} catch (CustomerDetailsNotFoundException | BillingServicesDownException e) {
			return new ModelAndView("/allPostpaidAccountDetailsPage", "error", e.getMessage());
		}
		
	}

	@RequestMapping("changePostpaidAccountPlan")
	public ModelAndView changePlanAction(@RequestParam("customerID") int customerID,
			@RequestParam("mobileNo") long mobileNo, @RequestParam("planID") int planID){
		try {
			billingServices.changePlan(customerID, mobileNo, planID);
			postpaidAccount = billingServices.getPostPaidAccountDetails(customerID, mobileNo);
			return new ModelAndView("/postpaidAccountChangePlanSuccessPage", "postpaidAccount", postpaidAccount);
		} catch (CustomerDetailsNotFoundException | PostpaidAccountNotFoundException | PlanDetailsNotFoundException
				| BillingServicesDownException e) {
			return new ModelAndView("/postpaidAccountChangePlanPage", "error", e.getMessage());
		}
		
		
	}
	
}
