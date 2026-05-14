package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserType;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryIT extends AbstractRepositoryDatabaseIT {

    @Test
    void shouldFindUserByPhone() {
        User saved = userRepository.save(new User(
                "john-keycloak-id",
                "John",
                UserType.CUSTOMER,
                "+79990000001"
        ));

        var found = userRepository.findByPhone("+79990000001");

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    void shouldFindUserByKeycloakId() {
        User saved = userRepository.save(new User(
                "john-keycloak-id-2",
                "John",
                UserType.CUSTOMER,
                "+79990000002"
        ));

        var found = userRepository.findByKeycloakId("john-keycloak-id-2");

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }
}
