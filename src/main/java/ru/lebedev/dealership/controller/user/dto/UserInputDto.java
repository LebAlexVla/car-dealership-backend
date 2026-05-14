package ru.lebedev.dealership.controller.user.dto;

public record UserInputDto(
        String keycloakId,
        String name,
        String type,
        String phone
) {
}