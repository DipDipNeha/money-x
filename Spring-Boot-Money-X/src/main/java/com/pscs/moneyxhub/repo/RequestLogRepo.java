package com.pscs.moneyxhub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyxhub.entity.RequestLog;

@Repository
public interface RequestLogRepo extends JpaRepository<RequestLog, Long> {

}
