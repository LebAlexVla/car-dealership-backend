package ru.lebedev.dealership.controller.user.dto;

public record UserOutputDto(
    Long id,
    String name,
    String type,
    String phone
) {
}