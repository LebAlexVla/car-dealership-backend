package ru.lebedev.dealership.application.permissions;

import ru.lebedev.dealership.domain.user.UserType;

public class TestDriveShowPermission implements Permission {
    @Override
    public boolean check(UserType userType) {
        return userType == UserType.MANAGER || userType == UserType.SYSTEM_ADMINISTRATOR;
    }
}