/**
 * @author Dipak
 */

package com.pscs.moneyxhub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pscs.moneyxhub.entity.MobCustomerMaster;

import jakarta.transaction.Transactional;

@Repository
public interface MobCustomerMasterRepo extends JpaRepository<MobCustomerMaster, Long> {

	MobCustomerMaster findByUserNameAndPassword(String userName, String password);

	MobCustomerMaster findByUserName(String userName);



	@Modifying
	@Query(value = "UPDATE MOB_CUSTOMER_MASTER SET retry_login_attempt = retry_login_attempt + 1 WHERE USER_ID = :userName", nativeQuery = true)
	int incrementRetryLoginAttemptNative(@Param("userName") String userName);

    @Modifying
    @Query(value = "UPDATE MOB_CUSTOMER_MASTER SET retry_login_attempt = 0 WHERE USER_ID = :userName", nativeQuery = true)
    int resetRetryLoginAttempt(@Param("userName") String userName);
    
    
    
    @Modifying
    @Transactional
    @Query("UPDATE MobCustomerMaster c SET c.isLocked = :isLocked WHERE c.userName = :userName")
    int updateIsLockedByUserName(@Param("isLocked") String isLocked, @Param("userName") String userName);
    

    MobCustomerMaster findByEmailAddressOrUserName(String string, String string2);

    MobCustomerMaster findByCustomerId(String customerId);

}
