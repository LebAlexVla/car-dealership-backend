package ru.lebedev.dealership.application.services;

import org.springframework.stereotype.Service;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.infrastructure.persistence.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long create(User user) {
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}