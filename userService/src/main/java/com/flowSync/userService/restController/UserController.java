package com.flowSync.userService.restController;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowSync.userService.dto.CreateUserProfileRequest;
import com.flowSync.userService.dto.UserResponse;
import com.flowSync.userService.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
     private final UserService userService;

    @PostMapping("/internal")
    public ResponseEntity<UserResponse> create(
            @RequestBody @Valid CreateUserProfileRequest request) {
                log.info("in controller level");
        return ResponseEntity.ok(userService.createUser(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUser(id));
    }


}
