package ru.lebedev.dealership.domain.user;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.BaseEntity;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType type;

    @Column(nullable = false)
    private String phone;

    protected User() {
    }

    public User(String name, UserType type, String phone) {
        this.name = name;
        this.type = type;
        this.phone = phone;
    }

    public UserType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}