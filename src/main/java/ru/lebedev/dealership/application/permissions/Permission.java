package ru.lebedev.dealership.application.permissions;

import ru.lebedev.dealership.domain.user.UserType;

public interface Permission {
    boolean check(UserType userType);
}
