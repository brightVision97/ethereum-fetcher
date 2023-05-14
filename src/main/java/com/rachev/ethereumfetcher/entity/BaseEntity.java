package com.rachev.ethereumfetcher.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class BaseEntity {

    @CreationTimestamp
    @JsonIgnore
    protected LocalDateTime created;

    @UpdateTimestamp
    @JsonIgnore
    protected LocalDateTime updated;
}
