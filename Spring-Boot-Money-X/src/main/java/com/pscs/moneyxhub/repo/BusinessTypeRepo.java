package com.pscs.moneyxhub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyxhub.entity.BusinessType;
@Repository
public interface BusinessTypeRepo extends JpaRepository<BusinessType, Long> {

}
