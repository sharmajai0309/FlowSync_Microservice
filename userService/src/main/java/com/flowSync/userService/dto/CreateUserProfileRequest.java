package com.flowSync.userService.dto;

import java.util.UUID;

import jakarta.validation.constraints.*;
import lombok.*;


@Data
public class CreateUserProfileRequest {

    private UUID id;
    
     @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;
}
