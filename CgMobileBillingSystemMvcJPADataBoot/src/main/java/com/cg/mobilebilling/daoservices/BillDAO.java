package com.cg.mobilebilling.daoservices;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.mobilebilling.beans.Bill;
import com.cg.mobilebilling.beans.PostpaidAccount;

public interface BillDAO extends JpaRepository<Bill, Integer> {
	/*@Query("select a from Bill a where a.postPaidAccount =:postPaidAccount and a.billMonth =:billMonth")
	Bill getMonthlyBill(@Param("postPaidAccount") PostpaidAccount postPaidAccount,@Param("billMonth") String billMonth);
	@Query("select a from Bill a where a.postPaidAccount=:postPaidAccount")
	List<Bill> getCustomerPostPaidAccountAllBills(@Param("postPaidAccount") PostpaidAccount postPaidAccount);
*/
	
	@Query(value="select * from Bill where mobile_no=:mobileno and bill_month =:billMonth",nativeQuery=true)
	Bill getMonthlyBill(@Param("mobileno") Long mobileno,@Param("billMonth") String billMonth);
	
	@Query(value="select * from Bill where mobile_no=:mobileno",nativeQuery=true)
	List<Bill> getCustomerPostPaidAccountAllBills(@Param("mobileno") Long mobileno);
}
