package com.pscs.moneyxhub.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pscs.moneyxhub.entity.MoneyXBusiness;

@Repository
public interface MoneyXBusinessRepo extends JpaRepository<MoneyXBusiness, Long> {

	MoneyXBusiness findByUserNameAndPassword(String userName, String password);

	MoneyXBusiness findByUserName(String userName);

	MoneyXBusiness findByAccountNumberOrUserName(String accountNumber,String userName);

//    @Modifying
//    @Query("UPDATE MONEYX_BUSINESS_TBL m SET m.retryLoginAttempt = :retryLoginAttempt WHERE m.userName = :userName")
//    int updateRetryLoginAttemptByUserName(@Param("retryLoginAttempt") int retryLoginAttempt,
//                                          @Param("userName") String userName);

	@Modifying
	@Query(value = "UPDATE MONEYX_BUSINESS_TBL SET retry_login_attempt = retry_login_attempt + 1 WHERE user_name = :userName", nativeQuery = true)
	int incrementRetryLoginAttemptNative(@Param("userName") String userName);

    @Modifying
    @Query("UPDATE MONEYX_BUSINESS_TBL m SET m.isLocked = :isLocked WHERE m.userName = :userName")
    int updateIsLockedByUserName(@Param("isLocked") String isLocked, @Param("userName") String userName);
    @Modifying
    @Query(value = "UPDATE MONEYX_BUSINESS_TBL SET retry_login_attempt = 0 WHERE user_name = :userName", nativeQuery = true)
    int resetRetryLoginAttempt(@Param("userName") String userName);

	MoneyXBusiness findByEmailAddressOrUserName(String string, String string2);

	MoneyXBusiness findByCustomerId(String customerId);

}
