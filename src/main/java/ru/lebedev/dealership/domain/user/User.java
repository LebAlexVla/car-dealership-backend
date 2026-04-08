package ru.lebedev.dealership.domain.user;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.BaseEntity;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

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