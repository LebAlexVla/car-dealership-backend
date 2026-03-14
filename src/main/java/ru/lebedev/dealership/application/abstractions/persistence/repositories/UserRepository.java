package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.user.User;

public interface UserRepository {
    User save(User user);

    User findById(Long id);
}
