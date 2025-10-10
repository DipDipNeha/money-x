package com.pscs.moneyxhub.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyxhub.entity.MobileCustomer;

@Repository
public interface CustomerRepo extends JpaRepository<MobileCustomer, Long> {
	public Optional<MobileCustomer> findByPhone(String phone);

}
