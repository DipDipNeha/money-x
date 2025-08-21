package com.pscs.moneyx.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pscs.moneyx.entity.CustomerDocInfo;

public interface CustomerDocInfoRepo extends JpaRepository<CustomerDocInfo, String>{

}
