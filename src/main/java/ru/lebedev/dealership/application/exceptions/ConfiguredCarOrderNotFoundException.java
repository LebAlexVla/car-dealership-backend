package ru.lebedev.dealership.application.exceptions;

public class ConfiguredCarOrderNotFoundException extends ApplicationException {
    public ConfiguredCarOrderNotFoundException(Long orderId) {
        super("Configured cat order with id " + orderId + " not found");
    }
}