package com.pscs.moneyx.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyx.entity.BusinessRole;

@Repository

public interface BusinessRoleRepo extends JpaRepository<BusinessRole, Long> {

}
