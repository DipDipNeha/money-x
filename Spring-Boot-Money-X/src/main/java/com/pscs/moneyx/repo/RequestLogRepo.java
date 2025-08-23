package com.pscs.moneyx.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyx.entity.RequestLog;

@Repository
public interface RequestLogRepo extends JpaRepository<RequestLog, Long> {

}
