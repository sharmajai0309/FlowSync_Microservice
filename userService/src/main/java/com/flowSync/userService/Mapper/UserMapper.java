package com.flowSync.userService.Mapper;

import org.springframework.stereotype.Component;

import com.flowSync.userService.domain.UserProfile;
import com.flowSync.userService.dto.UserResponse;

@Component
public class UserMapper {
	
	public UserResponse toResponse(UserProfile user) {
		return UserResponse.builder()
		.id(user.getId())
		.firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
	}

}
