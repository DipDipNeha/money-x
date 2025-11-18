/**
 * @author Dipak
 */

package com.pscs.moneyxhub.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyxhub.entity.SubscriptionPayments;

@Repository
public interface SubscriptionPaymentsRepo extends JpaRepository<SubscriptionPayments, Long> {

	 Optional<Object> findByTransactionReference(String string);

	 SubscriptionPayments findByMobileNumber(String string);

}
