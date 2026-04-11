package ru.lebedev.dealership.application.exceptions;

public class StockOrderNotFoundException extends ApplicationException {
    public StockOrderNotFoundException(Long stockOrderId) {
        super("Stock order with id " + stockOrderId + " not found");
    }
}