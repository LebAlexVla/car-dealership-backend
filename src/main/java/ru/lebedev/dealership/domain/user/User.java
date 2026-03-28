package ru.lebedev.dealership.domain.user;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType type;

    protected User() {
    }

    public User(UserType type) {
        this.type = type;
    }

    public UserType getType() {
        return type;
    }
}