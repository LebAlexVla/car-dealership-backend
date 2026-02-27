package ru.lebedev.dealership.application.permissions;

import ru.lebedev.dealership.domain.user.UserType;

public class CarAddPermission implements Permission {
    @Override
    public boolean check(UserType userType) {
        return userType == UserType.WAREHOUSE_ADMIN || userType == UserType.SYSTEM_ADMINISTRATOR;
    }
}
