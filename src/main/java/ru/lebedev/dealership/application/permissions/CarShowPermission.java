package ru.lebedev.dealership.application.permissions;

import ru.lebedev.dealership.domain.user.UserType;

public class CarShowPermission implements Permission {
    @Override
    public boolean check(UserType userType) {
        return userType == UserType.STORAGE_ADMIN
                || userType == UserType.CUSTOMER
                || userType == UserType.MANAGER
                || userType == UserType.SYSTEM_ADMINISTRATOR;
    }
}
