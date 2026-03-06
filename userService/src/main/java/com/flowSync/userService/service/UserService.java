package com.flowSync.userService.service;

import java.util.UUID;

import com.flowSync.userService.dto.CreateUserProfileRequest;
import com.flowSync.userService.dto.UserResponse;

public interface UserService {
    public UserResponse createUser(CreateUserProfileRequest request);
    public UserResponse getUser(UUID id);

}
