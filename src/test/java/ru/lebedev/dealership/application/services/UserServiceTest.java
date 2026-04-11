package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lebedev.dealership.infrastructure.persistence.repository.UserRepository;
import ru.lebedev.dealership.support.TestDataFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void shouldCreateFindAndDeleteUser() {
        UserService service = new UserService(userRepository);
        var user = TestDataFactory.user(1L);

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByPhone(user.getPhone())).thenReturn(Optional.of(user));
        when(userRepository.findAll()).thenReturn(List.of(user));

        assertEquals(1L, service.create(user));
        assertEquals(Optional.of(user), service.findById(1L));
        assertEquals(Optional.of(user), service.findByPhone(user.getPhone()));
        assertEquals(1, service.findAll().size());

        service.delete(1L);
        verify(userRepository).deleteById(1L);
    }
}
