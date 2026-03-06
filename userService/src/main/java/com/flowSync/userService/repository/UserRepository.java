package com.flowSync.userService.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowSync.userService.domain.UserProfile;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, UUID> {
	
	Optional<UserProfile> findByEmail(String email);

    boolean existsByEmail(String email);

}
