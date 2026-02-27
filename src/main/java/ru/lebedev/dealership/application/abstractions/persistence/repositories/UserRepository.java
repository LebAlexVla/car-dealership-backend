package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.user.User;

import java.util.UUID;

public interface UserRepository {
    User save(User user);

    User findById(UUID id);
}
