package com.cg.mobilebilling.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.mobilebilling.beans.Bill;
import com.cg.mobilebilling.beans.Customer;
import com.cg.mobilebilling.beans.Plan;
import com.cg.mobilebilling.beans.PostpaidAccount;
import com.cg.mobilebilling.daoservices.BillDAO;
import com.cg.mobilebilling.daoservices.CustomerDAO;
import com.cg.mobilebilling.daoservices.PlanDAO;
import com.cg.mobilebilling.daoservices.PostPaidAccountDAO;
import com.cg.mobilebilling.exceptions.BillDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.BillingServicesDownException;
import com.cg.mobilebilling.exceptions.CustomerDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.InvalidBillMonthException;
import com.cg.mobilebilling.exceptions.PlanDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.PostpaidAccountNotFoundException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component(value = "billingServices")
public class BillingServicesImpl implements BillingServices {
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private BillDAO billDAO;
	@Autowired
	private PostPaidAccountDAO postPaidAccountDAO;
	@Autowired
	private PlanDAO planDAO;

	private PostpaidAccount postpaidAccount;
	private Plan plan;
	private Customer customer;
	private Bill bill;
	private List<Customer> customers;
	private Map<Long, PostpaidAccount> postpaidAccounts;
	private List<Bill> bills;
	private Plan plan1 = new Plan(101, 300, 50, 20, 50, 50, 2000, 0.1f, 0.5f, 0.1f, 0.1f, 0.1f, "UP EAST",
			"Violet Base");
	private Plan plan2 = new Plan(102, 400, 100, 50, 50, 50, 5000, 0.1f, 0.5f, 0.1f, 0.1f, 0.1f, "PUNJAB",
			"Violet Entertainment");
	private Plan plan3 = new Plan(103, 500, 150, 100, 50, 50, 10000, 0.1f, 0.5f, 0.1f, 0.1f, 0.1f, "UP WEST",
			"Violet Entertainment+");

	@Override
	public List<Plan> getPlanAllDetails() throws BillingServicesDownException {
		return planDAO.findAll();
	}

	@Override
	public Customer acceptCustomerDetails(Customer customer) throws BillingServicesDownException {
		planDAO.save(plan1);
		planDAO.save(plan2);
		planDAO.save(plan3);
		customer = customerDAO.save(customer);
		return customer;
	}

	@Override
	public PostpaidAccount openPostpaidMobileAccount(int customerID, int planID)
			throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException, BillingServicesDownException {
		plan = planDAO.findById(planID)
				.orElseThrow(() -> (new PlanDetailsNotFoundException("Plan not found for Plan ID: " + planID)));
		customer = customerDAO.findById(customerID).orElseThrow(
				() -> (new CustomerDetailsNotFoundException("Customer not found for Customer ID: " + customerID)));
		
		long leftLimit = 9885238824L;
		long rightLimit = 9895238824L;
		long generatedMobileNumber = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
		postpaidAccount = new PostpaidAccount(generatedMobileNumber, plan, customer);
		
		//postpaidAccount=new PostpaidAccount(plan, customer);
		postpaidAccount = postPaidAccountDAO.save(postpaidAccount);
		return postpaidAccount;
	}

	@Override
	public Bill generateMonthlyMobileBill(int customerID, long mobileNo, String billMonth, int noOfLocalSMS,
			int noOfStdSMS, int noOfLocalCalls, int noOfStdCalls, int internetDataUsageUnits)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException,
			BillingServicesDownException, PlanDetailsNotFoundException {
		customer = customerDAO.findById(customerID).orElseThrow(
				() -> (new CustomerDetailsNotFoundException("Customer not found for Customer ID: " + customerID)));
		;
		postpaidAccount = postPaidAccountDAO.findById(mobileNo).orElseThrow(() -> (new PostpaidAccountNotFoundException(
				"PostpaidAccount not found for Mobile Number:" + mobileNo)));
		;
		plan = postpaidAccount.getPlan();
		bill = new Bill(noOfLocalSMS, noOfStdSMS, noOfLocalCalls, noOfStdCalls, internetDataUsageUnits, billMonth,
				postpaidAccount);
		if ((noOfLocalSMS - plan.getFreeLocalSMS()) < 0)
			bill.setLocalSMSAmount(0);
		else
			bill.setLocalSMSAmount(plan.getLocalSMSRate() * (noOfLocalSMS - plan.getFreeLocalSMS()));
		if ((noOfStdSMS - plan.getFreeStdSMS()) < 0)
			bill.setStdSMSAmount(0);
		else
			bill.setStdSMSAmount(plan.getStdSMSRate() * (noOfStdSMS - plan.getFreeStdSMS()));
		if ((noOfLocalCalls - plan.getFreeLocalCalls()) < 0)
			bill.setLocalCallAmount(0);
		else
			bill.setLocalCallAmount(plan.getLocalCallRate() * (noOfLocalCalls - plan.getFreeLocalCalls()));
		if ((noOfStdCalls - plan.getFreeStdCalls()) < 0)
			bill.setStdCallAmount(0);
		else
			bill.setStdCallAmount(plan.getStdCallRate() * (noOfStdCalls - plan.getFreeStdCalls()));
		if ((internetDataUsageUnits - plan.getFreeInternetDataUsageUnits()) < 0)
			bill.setInternetDataUsageAmount(0);
		else
			bill.setInternetDataUsageAmount(
					plan.getInternetDataUsageRate() * (internetDataUsageUnits - plan.getFreeInternetDataUsageUnits()));
		bill.setTotalBillAmount(bill.getLocalCallAmount() + bill.getStdCallAmount() + bill.getLocalSMSAmount()
				+ bill.getStdSMSAmount() + bill.getInternetDataUsageAmount() + plan.getMonthlyRental());
		bill.setServicesTax((float) (bill.getTotalBillAmount() * 0.025));
		bill.setVat((float) (bill.getTotalBillAmount() * 0.025));
		bill.setTotalBillAmount(bill.getTotalBillAmount() + bill.getServicesTax() + bill.getVat());
		billDAO.save(bill);
		return bill;
	}

	@Override
	public Customer getCustomerDetails(int customerID)
			throws CustomerDetailsNotFoundException, BillingServicesDownException {
		customer = customerDAO.findById(customerID).orElseThrow(
				() -> (new CustomerDetailsNotFoundException("Customer not found for Customer ID: " + customerID)));
		;
		return customer;
	}

	@Override
	public List<Customer> getAllCustomerDetails()
			throws BillingServicesDownException, CustomerDetailsNotFoundException {
		customers = customerDAO.findAll();
		if (customers.size() == 0)
			throw new CustomerDetailsNotFoundException("No Customers present in DataBase");
		return customers;
	}

	@Override
	public PostpaidAccount getPostPaidAccountDetails(int customerID, long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException {
		customer = customerDAO.findById(customerID).orElseThrow(
				() -> (new CustomerDetailsNotFoundException("Customer not found for Customer ID: " + customerID)));
		;
		return postPaidAccountDAO.findById(mobileNo).orElseThrow(() -> (new PostpaidAccountNotFoundException(
				"PostpaidAccount not found for Mobile Number:" + mobileNo)));
	}

	@Override
	public List<PostpaidAccount> getCustomerAllPostpaidAccountsDetails(int customerID)
			throws CustomerDetailsNotFoundException, BillingServicesDownException {
		customer = customerDAO.findById(customerID).orElseThrow(
				() -> (new CustomerDetailsNotFoundException("Customer not found for Customer ID: " + customerID)));
		;
		return postPaidAccountDAO.getCustomerAllPostpaidAccountsDetails(customer);
	}

	@Override
	public Bill getMobileBillDetails(int customerID, long mobileNo, String billMonth)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException,
			BillDetailsNotFoundException, BillingServicesDownException {
		customer = customerDAO.findById(customerID).orElseThrow(
				() -> (new CustomerDetailsNotFoundException("Customer not found for Customer ID: " + customerID)));
		;
		postpaidAccount = postPaidAccountDAO.findById(mobileNo).orElseThrow(() -> (new PostpaidAccountNotFoundException(
				"PostpaidAccount not found for Mobile Number:" + mobileNo)));
		;
		bill = billDAO.getMonthlyBill(mobileNo, billMonth);
		// System.out.println(bill);

		return bill;
	}

	@Override
	public List<Bill> getCustomerPostPaidAccountAllBillDetails(int customerID, long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException,
			BillDetailsNotFoundException {
		customer = customerDAO.findById(customerID).orElseThrow(
				() -> (new CustomerDetailsNotFoundException("Customer not found for Customer ID: " + customerID)));
		;
		postpaidAccount = postPaidAccountDAO.findById(mobileNo).orElseThrow(() -> (new PostpaidAccountNotFoundException(
				"PostpaidAccount not found for Mobile Number:" + mobileNo)));
		;
		bills = billDAO.getCustomerPostPaidAccountAllBills(mobileNo);

		return bills;
	}

	@Override
	public boolean changePlan(int customerID, long mobileNo, int planID) throws CustomerDetailsNotFoundException,
			PostpaidAccountNotFoundException, PlanDetailsNotFoundException, BillingServicesDownException {
		customer = customerDAO.findById(customerID).orElseThrow(
				() -> (new CustomerDetailsNotFoundException("Customer not found for Customer ID: " + customerID)));
		;
		postpaidAccount = postPaidAccountDAO.findById(mobileNo).orElseThrow(() -> (new PostpaidAccountNotFoundException(
				"PostpaidAccount not found for Mobile Number:" + mobileNo)));
		;
		postpaidAccount.setPlan(planDAO.findById(planID).orElseThrow(() -> (new PlanDetailsNotFoundException("Plan not found for Plan ID: " + planID))));
		postPaidAccountDAO.save(postpaidAccount);
		return true;
	}

	@Override
	public boolean closeCustomerPostPaidAccount(int customerID, long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException {
		customer = customerDAO.findById(customerID).orElseThrow(
				() -> (new CustomerDetailsNotFoundException("Customer not found for Customer ID: " + customerID)));
		;
		postpaidAccounts = customer.getPostpaidAccounts();
		postpaidAccount = postPaidAccountDAO.findById(mobileNo).orElseThrow(() -> (new PostpaidAccountNotFoundException(
				"PostpaidAccount not found for Mobile Number:" + mobileNo)));
		;
		postPaidAccountDAO.deleteById(postpaidAccount.getMobileNo());// Not Working
		postPaidAccountDAO.deletePostPaidAccount(mobileNo);
		return false;
	}

	@Override
	public boolean deleteCustomer(int customerID)
			throws BillingServicesDownException, CustomerDetailsNotFoundException {
		/*
		 * customer = customerDAO.findById(customerID).get();
		 * postpaidAccounts=customer.getPostpaidAccounts(); Iterable<PostpaidAccount>
		 * postpaidAccountToBeDeleted = postpaidAccounts.values();
		 * postPaidAccountDAO.deleteInBatch(postpaidAccountToBeDeleted);
		 */
		customer = customerDAO.findById(customerID).orElseThrow(
				() -> (new CustomerDetailsNotFoundException("Customer not found for Customer ID: " + customerID)));
		;
		customerDAO.deleteById(customerID);
		return false;
	}

	@Override
	public Plan getCustomerPostPaidAccountPlanDetails(int customerID, long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException,
			PlanDetailsNotFoundException {
		customer = customerDAO.findById(customerID).orElseThrow(
				() -> (new CustomerDetailsNotFoundException("Customer not found for Customer ID: " + customerID)));
		postpaidAccount = postPaidAccountDAO.findById(mobileNo).orElseThrow(() -> (new PostpaidAccountNotFoundException(
				"PostpaidAccount not found for Mobile Number:" + mobileNo)));
		plan = postpaidAccount.getPlan();
		return plan;
	}

}