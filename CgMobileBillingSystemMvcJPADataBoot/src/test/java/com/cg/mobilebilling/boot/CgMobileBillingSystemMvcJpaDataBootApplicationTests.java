package com.cg.mobilebilling.boot;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.cg.mobilebilling.beans.Address;
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
import com.cg.mobilebilling.services.BillingServices;
import com.itextpdf.text.DocumentException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CgMobileBillingSystemMvcJpaDataBootApplicationTests {

	@MockBean
	private CustomerDAO customerDAO;
	@MockBean
	private PlanDAO planDAO;
	@MockBean
	private PostPaidAccountDAO postpaidDAO;
	@MockBean
	private BillDAO billingDAO;
	@Autowired
	private BillingServices billingServices;
	private static Customer customer1, customer2;
	private static PostpaidAccount postpaid1, postpaid2;
	private static Plan plan1, plan2;
	private static Bill bill1, bill2;

	@Before
	public void setup() {
		customer1 = new Customer(101, "Kanwal", "Singh", "hkps@gmail.com", "13/12/1993",
				new Address(141002, "Ludhiana", "Punjab"));
		customer2 = new Customer(102, "Sahil", "Singh", "sahil@gmail.com", "12/11/1995",
				new Address(221005, "Varanasi", "UP"));
		plan1 = new Plan(101, 300, 50, 20, 50, 50, 2000, 0.1f, 0.5f, 0.1f, 0.1f, 0.1f, "UP EAST", "Violet Base");
		plan2 = new Plan(102, 400, 100, 50, 50, 50, 5000, 0.1f, 0.5f, 0.1f, 0.1f, 0.1f, "PUNJAB",
				"Violet Entertainment");
		postpaid1 = new PostpaidAccount(9876500001L, plan1, customer1);
		postpaid2 = new PostpaidAccount(9876500001L, plan2, customer1);
		bill1 = new Bill(101, 500, 200, 400, 200, 500, "January", 509.25f, 45.0f, 15.0f, 35.0f, 90.0f, 0.0f, 12.125f,
				12.125f, postpaid1);
		bill2 = new Bill(101, 500, 200, 400, 200, 500, "May", 593.25f, 45.0f, 15.0f, 30.0f, 75.0f, 0.0f, 14.125f,
				14.125f, postpaid2);
	}

	@Test
	public void acceptCustomerDetailsTest() throws CustomerDetailsNotFoundException, BillingServicesDownException {
		Mockito.when(customerDAO.save(customer1)).thenReturn(customer1);
		assertThat(billingServices.acceptCustomerDetails(customer1)).isEqualTo(customer1);
	}

	@Test
	public void getCustomerDetailsTest() throws CustomerDetailsNotFoundException, BillingServicesDownException {
		Mockito.when(customerDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(customer1));
		assertThat(billingServices.getCustomerDetails(101)).isEqualTo(customer1);
	}

	@Test(expected = CustomerDetailsNotFoundException.class)
	public void getCustomerDetailsInvalidCustomerTest()
			throws CustomerDetailsNotFoundException, BillingServicesDownException {
		billingServices.getCustomerDetails(101);
	}

	@Test
	public void getAllCustomerDetailsTest() throws BillingServicesDownException, CustomerDetailsNotFoundException {
		ArrayList<Customer> list = new ArrayList<>();
		list.add(customer1);
		list.add(customer2);
		Mockito.when(customerDAO.findAll()).thenReturn(list);
		assertThat(billingServices.getAllCustomerDetails()).isEqualTo(list);
	}

	@Test
	public void getAllPlanDetailsTest()
			throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException, BillingServicesDownException {
		ArrayList<Plan> list = new ArrayList<>();
		list.add(plan1);
		list.add(plan2);
		Mockito.when(planDAO.findAll()).thenReturn(list);
		assertThat(billingServices.getPlanAllDetails()).isEqualTo(list);
	}

	@Test
	public void openPostpaidMobileAccountTest()
			throws CustomerDetailsNotFoundException, PlanDetailsNotFoundException, BillingServicesDownException {
		Mockito.when(customerDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(customer1));
		Mockito.when(planDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(plan1));
		Mockito.when(postpaidDAO.save(Mockito.any(PostpaidAccount.class))).thenReturn(postpaid1);
		assertThat(billingServices.openPostpaidMobileAccount(101, 1).getMobileNo()).isEqualTo(9876500001L);
	}

	/*@Test(expected = CustomerDetailsNotFoundException.class)
	public void invalidCustomerOpenPostpaidMobileAccountTest()
			throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException, BillingServicesDownException {
		billingServices.openPostpaidMobileAccount(103, 101);
	}*/

	@Test(expected = PlanDetailsNotFoundException.class)
	public void invalidPlanOpenPostpaidMobileAccountTest()
			throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException, BillingServicesDownException {
		Mockito.when(customerDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(customer1));
		billingServices.openPostpaidMobileAccount(101, 11);
	}

	@Test
	public void getPostPaidAccountDetailsTest()
			throws CustomerDetailsNotFoundException, BillingServicesDownException, PostpaidAccountNotFoundException {
		Mockito.when(customerDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(customer1));
		Mockito.when(postpaidDAO.findById(Mockito.anyLong())).thenReturn(Optional.of(postpaid1));
		assertThat(billingServices.getPostPaidAccountDetails(101, 9876500001L)).isEqualTo(postpaid1);
	}

	@Test(expected = CustomerDetailsNotFoundException.class)
	public void invalidCustomergetPostpaidMobileAccountTest() throws PlanDetailsNotFoundException,
			CustomerDetailsNotFoundException, BillingServicesDownException, PostpaidAccountNotFoundException {
		billingServices.getPostPaidAccountDetails(102, 9876500001L);
	}

	@Test(expected = PostpaidAccountNotFoundException.class)
	public void invalidAccountOpenPostpaidMobileAccountTest() throws PlanDetailsNotFoundException,
			CustomerDetailsNotFoundException, BillingServicesDownException, PostpaidAccountNotFoundException {
		Mockito.when(customerDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(customer1));
		billingServices.getPostPaidAccountDetails(101, 9876500001L);
	}

	@Test
	public void getAllPostPaidAccountDetailsTest() throws PlanDetailsNotFoundException,
			CustomerDetailsNotFoundException, BillingServicesDownException, PostpaidAccountNotFoundException {
		ArrayList<PostpaidAccount> list = new ArrayList<>();
		list.add(postpaid1);
		list.add(postpaid2);
		Mockito.when(customerDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(customer1));
		Mockito.when(postpaidDAO.getCustomerAllPostpaidAccountsDetails(Mockito.any(Customer.class))).thenReturn(list);
		assertThat(billingServices.getCustomerAllPostpaidAccountsDetails(101)).isEqualTo(list);
	}

	@Test(expected = PostpaidAccountNotFoundException.class)
	public void invalidAccountsgetPostpaidMobileAccountsTest() throws PlanDetailsNotFoundException,
			CustomerDetailsNotFoundException, BillingServicesDownException, PostpaidAccountNotFoundException {
		Mockito.when(customerDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(customer1));
		billingServices.getCustomerAllPostpaidAccountsDetails(101);
	}

	@Test(expected = CustomerDetailsNotFoundException.class)
	public void invalidCustomergetPostpaidMobileAccountsTest() throws PlanDetailsNotFoundException,
			CustomerDetailsNotFoundException, BillingServicesDownException, PostpaidAccountNotFoundException {
		billingServices.getPostPaidAccountDetails(102, 9876500001L);
	}

	/*@Test
	public void getMonthlyBillTest() throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException,
			BillingServicesDownException, PostpaidAccountNotFoundException, FileNotFoundException,
			InvalidBillMonthException, BillDetailsNotFoundException, DocumentException, IOException {
		Mockito.when(customerDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(customer1));
		Mockito.when(postpaidDAO.findById(Mockito.anyLong())).thenReturn(Optional.of(postpaid1));
		Mockito.when(billingDAO.getMonthlyBill(Mockito.any(PostpaidAccount.class).getMobileNo(), Mockito.anyString()))
				.thenReturn(bill1);
		assertThat(billingServices.getMobileBillDetails(101, 9876500001L, "January")).isEqualTo(bill1);
	}*/

	@Test(expected = CustomerDetailsNotFoundException.class)
	public void invalidCustomergetMonthlyBillTest()
			throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException, BillingServicesDownException,
			PostpaidAccountNotFoundException, FileNotFoundException, InvalidBillMonthException,
			BillDetailsNotFoundException, DocumentException, IOException {
		billingServices.getMobileBillDetails(102, 9876500001L, "January");
	}

	@Test(expected = PostpaidAccountNotFoundException.class)
	public void invalidAccountgetMonthlyBillTest()
			throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException, BillingServicesDownException,
			PostpaidAccountNotFoundException, FileNotFoundException, InvalidBillMonthException,
			BillDetailsNotFoundException, DocumentException, IOException {
		Mockito.when(customerDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(customer1));
		billingServices.getMobileBillDetails(101, 9876500001L, "January");
	}

	@Test(expected = BillDetailsNotFoundException.class)
	public void invalidBillgetMonthlyBillTest() throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException,
			BillingServicesDownException, PostpaidAccountNotFoundException, FileNotFoundException,
			InvalidBillMonthException, BillDetailsNotFoundException, DocumentException, IOException {
		Mockito.when(customerDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(customer1));
		Mockito.when(postpaidDAO.findById(Mockito.anyLong())).thenReturn(Optional.of(postpaid1));
		billingServices.getMobileBillDetails(102, 9876500001L, "January");
	}

	@Test
	public void generateMonthlyBillTest() throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException,
			BillingServicesDownException, PostpaidAccountNotFoundException, FileNotFoundException,
			InvalidBillMonthException, BillDetailsNotFoundException, DocumentException, IOException {
		Mockito.when(customerDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(customer1));
		Mockito.when(postpaidDAO.findById(Mockito.anyLong())).thenReturn(Optional.of(postpaid1));
		Mockito.when(billingDAO.save(Mockito.any(Bill.class))).thenReturn(bill2);
		assertThat(billingServices.generateMonthlyMobileBill(101, 987650001, "May", 500, 200, 400, 200, 500))
				.isEqualTo(bill2);
	}

	@Test(expected = CustomerDetailsNotFoundException.class)
	public void invalidCustomergenerateMonthlyBillTest()
			throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException, BillingServicesDownException,
			PostpaidAccountNotFoundException, FileNotFoundException, InvalidBillMonthException,
			BillDetailsNotFoundException, DocumentException, IOException {
		billingServices.generateMonthlyMobileBill(101, 9876500001L, "May", 500, 200, 400, 200, 500);
	}

	@Test(expected = PostpaidAccountNotFoundException.class)
	public void invalidAccountgenerateMonthlyBillTest()
			throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException, BillingServicesDownException,
			PostpaidAccountNotFoundException, FileNotFoundException, InvalidBillMonthException,
			BillDetailsNotFoundException, DocumentException, IOException {
		Mockito.when(customerDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(customer1));
		billingServices.generateMonthlyMobileBill(101, 9876500001L, "May", 500, 200, 400, 200, 500);
	}

/*	@Test
	public void getAllMonthlyBillTest() throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException,
			BillingServicesDownException, PostpaidAccountNotFoundException, FileNotFoundException,
			InvalidBillMonthException, BillDetailsNotFoundException, DocumentException, IOException {
		ArrayList<Bill> list = new ArrayList<>();
		list.add(bill1);
		list.add(bill2);
		Mockito.when(customerDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(customer1));
		Mockito.when(postpaidDAO.findById(Mockito.anyLong())).thenReturn(Optional.of(postpaid1));
		Mockito.when(billingDAO.getCustomerPostPaidAccountAllBills(Mockito.any(PostpaidAccount.class).getMobileNo()))
				.thenReturn(list);
		assertThat(billingServices.getCustomerPostPaidAccountAllBillDetails(101, 9876500001L)).isEqualTo(list);
	}*/

	@Test(expected = CustomerDetailsNotFoundException.class)
	public void invalidCustomergetAllMonthlyBillsTest()
			throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException, BillingServicesDownException,
			PostpaidAccountNotFoundException, FileNotFoundException, InvalidBillMonthException,
			BillDetailsNotFoundException, DocumentException, IOException {
		billingServices.getCustomerPostPaidAccountAllBillDetails(101, 9876500001L);
	}

	@Test(expected = PostpaidAccountNotFoundException.class)
	public void invalidAccountgetAllMonthlyBillsTest()
			throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException, BillingServicesDownException,
			PostpaidAccountNotFoundException, FileNotFoundException, InvalidBillMonthException,
			BillDetailsNotFoundException, DocumentException, IOException {
		Mockito.when(customerDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(customer1));
		billingServices.getCustomerPostPaidAccountAllBillDetails(101, 9876500001L);
	}

	@Test(expected = BillDetailsNotFoundException.class)
	public void invalidBillgetAllMonthlyBillsTest()
			throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException, BillingServicesDownException,
			PostpaidAccountNotFoundException, FileNotFoundException, InvalidBillMonthException,
			BillDetailsNotFoundException, DocumentException, IOException {
		Mockito.when(customerDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(customer1));
		Mockito.when(postpaidDAO.findById(Mockito.anyLong())).thenReturn(Optional.of(postpaid1));
		billingServices.getCustomerPostPaidAccountAllBillDetails(101, 9876500001L);
	}
}