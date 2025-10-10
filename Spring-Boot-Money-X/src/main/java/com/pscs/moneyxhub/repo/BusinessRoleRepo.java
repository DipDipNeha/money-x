package com.pscs.moneyxhub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyxhub.entity.BusinessRole;

@Repository

public interface BusinessRoleRepo extends JpaRepository<BusinessRole, Long> {

}
