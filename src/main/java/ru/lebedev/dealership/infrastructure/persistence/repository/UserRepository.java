package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebedev.dealership.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}