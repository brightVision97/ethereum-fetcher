package com.rachev.ethereumfetcher.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EqualsAndHashCode
public abstract class BaseEntity {

    @CreationTimestamp
    @JsonIgnore
    protected LocalDateTime created;

    @UpdateTimestamp
    @JsonIgnore
    protected LocalDateTime updated;
}
