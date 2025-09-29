package com.pscs.moneyx.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyx.entity.WalletAcctData;

@Repository
public interface WalletAcctDataRepository extends JpaRepository<WalletAcctData, Long>{
	
}
