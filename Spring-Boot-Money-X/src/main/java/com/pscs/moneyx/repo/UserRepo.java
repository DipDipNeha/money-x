/**
 * 
 */
package com.pscs.moneyx.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyx.entity.UserEntity;

/**
 * 
 */
@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
public Optional<UserEntity> findByUsername(String username);
public boolean existsByUsername(String username);
}
