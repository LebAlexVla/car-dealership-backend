package ru.lebedev.dealership.domain.shared.vo;

public record Brand(String name) {
    public Brand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Brand name can't be null or blank");
        }
    }
}
