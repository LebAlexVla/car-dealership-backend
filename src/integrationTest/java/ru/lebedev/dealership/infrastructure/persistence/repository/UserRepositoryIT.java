package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserType;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryIT extends AbstractRepositoryDatabaseIT {

    @Test
    void shouldFindUserByPhone() {
        User saved = userRepository.save(new User("John", UserType.CUSTOMER, "+79990000001"));

        var found = userRepository.findByPhone("+79990000001");

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }
}
