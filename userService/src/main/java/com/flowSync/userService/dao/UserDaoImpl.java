package com.flowSync.userService.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.flowSync.userService.domain.UserProfile;
import com.flowSync.userService.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j      
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
	
	private final UserRepository useRepository;

	@Override
	public UserProfile save(UserProfile user) {
		log.info("In dao layer sending data to repo Layer");
		return useRepository.save(user);
	}
	 
	@Override
	public Optional<UserProfile> findById(UUID id) {
        return useRepository.findById(id);
    }

	@Override
    public boolean existsByEmail(String email) {
        return useRepository.existsByEmail(email);
    }

	

}
