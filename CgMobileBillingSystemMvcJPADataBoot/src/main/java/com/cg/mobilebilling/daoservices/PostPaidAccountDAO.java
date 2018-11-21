package com.cg.mobilebilling.daoservices;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.mobilebilling.beans.Customer;
import com.cg.mobilebilling.beans.PostpaidAccount;

public interface PostPaidAccountDAO extends JpaRepository<PostpaidAccount, Long> {
	@Modifying
	@Transactional
	@Query("delete from PostpaidAccount where mobileNo=:mobileNo")
	void deletePostPaidAccount(@Param("mobileNo")Long mobileNo);
	@Query("select a from PostpaidAccount a where a.customer=:customer")
	List<PostpaidAccount> getCustomerAllPostpaidAccountsDetails(@Param("customer") Customer customer);
}
