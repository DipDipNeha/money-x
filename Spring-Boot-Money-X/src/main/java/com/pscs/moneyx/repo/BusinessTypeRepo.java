package com.pscs.moneyx.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyx.entity.BusinessType;
@Repository
public interface BusinessTypeRepo extends JpaRepository<BusinessType, Long> {

}
