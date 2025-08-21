package com.pscs.moneyx.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyx.entity.Country;

@Repository
public interface CountryRepo extends JpaRepository<Country, Long> {

}
