/**
 * @author Dipak
 */

package com.pscs.moneyxhub.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyxhub.entity.CreditTransactionLog;
@Repository
public interface CreditTransactionLogRepo extends JpaRepository<CreditTransactionLog, Long> {

	List<CreditTransactionLog> findByUserId(String string);
	

}
