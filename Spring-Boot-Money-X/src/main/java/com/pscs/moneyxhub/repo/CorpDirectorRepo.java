/**
 * @author Dipak
 */

package com.pscs.moneyxhub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyxhub.entity.CorpDirector;

@Repository
public interface CorpDirectorRepo extends  JpaRepository<CorpDirector, Long>  {

	CorpDirector findByCustomerId(String string);
	

}
