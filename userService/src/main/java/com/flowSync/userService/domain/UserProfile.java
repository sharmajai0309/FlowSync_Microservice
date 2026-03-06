package com.flowSync.userService.domain;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "users")
public class UserProfile extends BaseEntity {
    @Id
    private UUID id;

     @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    public UserProfile(UUID id,
                       String firstName,
                       String lastName,
                       String email) {

        this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void updatePhone(String phone) {
        this.phone = phone;
    }



}
