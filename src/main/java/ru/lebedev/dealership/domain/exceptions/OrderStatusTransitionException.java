package ru.lebedev.dealership.domain.exceptions;

public class OrderStatusTransitionException extends DomainException {
    public OrderStatusTransitionException(String message) {
        super(message);
    }
}
