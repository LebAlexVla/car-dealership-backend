package ru.lebedev.dealership.domain.user;

import java.util.UUID;

public record User(UUID id, UserType type) {}