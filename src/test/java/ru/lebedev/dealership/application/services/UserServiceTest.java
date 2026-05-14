package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.infrastructure.persistence.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static ru.lebedev.dealership.TestDataFactory.customer;
import static ru.lebedev.dealership.TestDataFactory.manager;
import static ru.lebedev.dealership.TestDataFactory.systemAdmin;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void create_shouldSaveUserAndReturnId() {
        User user = customer(1L);

        when(userRepository.save(user)).thenReturn(user);

        Long result = userService.create(user);

        assertEquals(1L, result);
        verify(userRepository).save(user);
    }

    @Test
    void findById_shouldReturnUserWhenExists() {
        User user = customer(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(1L);

        assertEquals(Optional.of(user), result);
        verify(userRepository).findById(1L);
    }

    @Test
    void findById_shouldReturnEmptyWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isEmpty());
        verify(userRepository).findById(1L);
    }

    @Test
    void findByPhone_shouldReturnUserWhenExists() {
        User user = customer(1L);
        String phone = "+70000000001";

        when(userRepository.findByPhone(phone)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByPhone(phone);

        assertEquals(Optional.of(user), result);
        verify(userRepository).findByPhone(phone);
    }

    @Test
    void findByPhone_shouldReturnEmptyWhenUserNotFound() {
        String phone = "+79999999999";

        when(userRepository.findByPhone(phone)).thenReturn(Optional.empty());

        Optional<User> result = userService.findByPhone(phone);

        assertTrue(result.isEmpty());
        verify(userRepository).findByPhone(phone);
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        User customer = customer(1L);
        User manager = manager(2L);
        User admin = systemAdmin(3L);

        when(userRepository.findAll()).thenReturn(List.of(customer, manager, admin));

        List<User> result = userService.findAll();

        assertEquals(List.of(customer, manager, admin), result);
        verify(userRepository).findAll();
    }

    @Test
    void findAll_shouldReturnEmptyListWhenNoUsersExist() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<User> result = userService.findAll();

        assertTrue(result.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    void delete_shouldDeleteUserById() {
        userService.delete(1L);

        verify(userRepository).deleteById(1L);
    }
}