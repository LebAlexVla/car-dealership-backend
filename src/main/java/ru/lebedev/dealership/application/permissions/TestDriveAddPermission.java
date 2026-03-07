package ru.lebedev.dealership.application.permissions;

import ru.lebedev.dealership.domain.user.UserType;

public class TestDriveAddPermission implements Permission {
    @Override
    public boolean check(UserType userType) {
        return userType == UserType.CUSTOMER || userType == UserType.SYSTEM_ADMINISTRATOR;
    }
}