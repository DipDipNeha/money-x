/**
 * @author Dipak
 */

package com.pscs.moneyxhub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pscs.moneyxhub.entity.CorporateCustomer;

import jakarta.transaction.Transactional;


@Repository
public interface CorporateCustomerRepo extends JpaRepository<CorporateCustomer, Long> {

	
	CorporateCustomer findByUserNameAndPassword(String userName, String password);

	CorporateCustomer findByUserName(String userName);

	CorporateCustomer findByAccountNumberOrUserName(String accountNumber,String userName);
	@Modifying
	@Transactional
	@Query("UPDATE CorporateCustomer m SET m.isLocked = :isLocked WHERE m.userName = :userName")
	int updateIsLockedByUserName(@Param("isLocked") String isLocked, @Param("userName") String userName);


	@Modifying
	@Query(value = "UPDATE CORP_CUSTOMER_MASTER_TBL SET retry_login_attempt = retry_login_attempt + 1 WHERE user_name = :userName", nativeQuery = true)
	int incrementRetryLoginAttemptNative(@Param("userName") String userName);

    @Query(value = "UPDATE CORP_CUSTOMER_MASTER_TBL SET retry_login_attempt = 0 WHERE user_name = :userName", nativeQuery = true)
    int resetRetryLoginAttempt(@Param("userName") String userName);

    CorporateCustomer findByEmailOrUserName(String string, String string2);

    CorporateCustomer findByCustomerId(String customerId);
}
