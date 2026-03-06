package com.flowSync.userService.service;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.flowSync.userService.Mapper.UserMapper;
import com.flowSync.userService.dao.UserDao;
import com.flowSync.userService.domain.UserProfile;
import com.flowSync.userService.dto.CreateUserProfileRequest;
import com.flowSync.userService.dto.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
     private final UserDao userDao;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(CreateUserProfileRequest request) {
        log.info("In Service layer of Create user");
        if(userDao.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        UserProfile user = UserProfile.builder()
        .id(request.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
                log.info("Sending datato dao Layer");
        return userMapper.toResponse(userDao.save(user));
    }

    @Override
    public UserResponse getUser(UUID id) {
       return userMapper.toResponse(userDao.findById(id).orElseThrow(()-> new RuntimeException("Cant find the id : " + id)));
    }

    
}
