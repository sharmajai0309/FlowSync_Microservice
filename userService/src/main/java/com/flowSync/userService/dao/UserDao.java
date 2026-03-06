package com.flowSync.userService.dao;

import java.util.Optional;
import java.util.UUID;

import com.flowSync.userService.domain.UserProfile;

public interface UserDao {

	UserProfile save(UserProfile user);

	Optional<UserProfile> findById(UUID id);

	boolean existsByEmail(String email);

}
