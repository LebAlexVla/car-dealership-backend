package ru.lebedev.dealership.controller.user.dto;

public record UserInputDto(
        String name,
        String type,
        String phone
) {
}