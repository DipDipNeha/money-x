package com.pscs.moneyxhub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyxhub.entity.Document;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {

	Iterable<Document> findByCountryCode(String countryId);

}
