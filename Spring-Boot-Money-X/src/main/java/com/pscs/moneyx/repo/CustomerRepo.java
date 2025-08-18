package com.pscs.moneyx.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyx.entity.MobileCustomer;

@Repository
public interface CustomerRepo extends JpaRepository<MobileCustomer, Long> {
	public Optional<MobileCustomer> findByPhone(String phone);

}
