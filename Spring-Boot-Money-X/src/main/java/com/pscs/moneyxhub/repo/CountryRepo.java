package com.pscs.moneyxhub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyxhub.entity.Country;

@Repository
public interface CountryRepo extends JpaRepository<Country, Long> {

}
