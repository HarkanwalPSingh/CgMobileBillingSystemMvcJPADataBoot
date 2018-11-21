package com.cg.mobilebilling.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cg.mobilebilling.beans.Customer;
import com.cg.mobilebilling.exceptions.BillingServicesDownException;
import com.cg.mobilebilling.exceptions.CustomerDetailsNotFoundException;
import com.cg.mobilebilling.services.BillingServices;

@Controller
public class CustomerController {
	@Autowired
	private BillingServices billingServices;
	private Customer customer;
	private List<Customer> customers;

	@RequestMapping("/registerCustomer")//@Valid was not added...................
	public ModelAndView registerCustomerAction(@ Valid @ModelAttribute("customer") Customer customer, BindingResult result) {
		if (result.hasErrors())
			return new ModelAndView("addCustomerPage");
		try {
			customer = billingServices.acceptCustomerDetails(customer);
			return new ModelAndView("/addCustomerSuccessPage", "customer", customer);
		} catch (BillingServicesDownException e) {
			return new ModelAndView("addCustomerPage", "error", e.getMessage());
		}
	}

	@RequestMapping("/deleteCustomer")
	public ModelAndView removeCustomerAction(@RequestParam int customerID) {
		try {
			billingServices.deleteCustomer(customerID);
			return new ModelAndView("/removeCustomerSuccessPage", "customerID", customerID);
		} catch (BillingServicesDownException | CustomerDetailsNotFoundException e) {
			return new ModelAndView("/removeCustomerPage", "error", e.getMessage());
		}

	}

	@RequestMapping("/getCustomerDetails")
	public ModelAndView getCustomerDetailsAction(@RequestParam int customerID) {
		try {
			customer = billingServices.getCustomerDetails(customerID);
			return new ModelAndView("/customerDetailsSuccessPage", "customer", customer);
		} catch (CustomerDetailsNotFoundException | BillingServicesDownException e) {
			return new ModelAndView("/customerDetailsPage", "error", e.getMessage());
		}

	}

	@RequestMapping("/allCustomerDetails")
	public ModelAndView allCustomerDetailsAction() {
		try {
			customers = billingServices.getAllCustomerDetails();
			return new ModelAndView("allCustomerDetailsPage", "customers", customers);
		} catch (BillingServicesDownException | CustomerDetailsNotFoundException e) {
			return new ModelAndView("/customerDetailsPage", "error", e.getMessage());
		}
	}

}
