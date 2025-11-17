/**
 * @author Dipak
 */

package com.pscs.moneyxhub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyxhub.entity.SubscriptionAddons;

@Repository
public interface SubscriptionAddonsRepo extends JpaRepository<SubscriptionAddons, Long> {

}
