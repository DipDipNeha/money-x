package com.pscs.moneyx.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyx.entity.Document;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {

	Iterable<Document> findByCountryCode(String countryId);

}
