package ru.lebedev.dealership.domain;

import jakarta.persistence.*;

import java.time.Instant;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    protected Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    protected Instant updatedAt;

    @Column(nullable = false)
    protected boolean removed;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof BaseEntity entity))
            return false;

        return id != null && id.equals(entity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}