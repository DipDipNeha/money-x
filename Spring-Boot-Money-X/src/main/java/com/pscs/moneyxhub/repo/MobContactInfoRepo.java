/**
 * @author Dipak
 */

package com.pscs.moneyxhub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyxhub.entity.MobContactInfo;

@Repository
public interface MobContactInfoRepo extends JpaRepository<MobContactInfo,Long>{

	MobContactInfo findByCustId(String userId);

}
