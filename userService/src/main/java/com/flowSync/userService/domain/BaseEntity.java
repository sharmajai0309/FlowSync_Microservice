package com.flowSync.userService.domain;
import jakarta.persistence.*;
import lombok.Getter;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class BaseEntity {


    

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public void setId(UUID id) {
        this.id = id;
    }



}
