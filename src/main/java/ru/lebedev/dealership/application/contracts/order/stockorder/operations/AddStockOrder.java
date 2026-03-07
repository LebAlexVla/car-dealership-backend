package ru.lebedev.dealership.application.contracts.order.stockorder.operations;

public final class AddStockOrder {
    private AddStockOrder(){
    }

    public record Request(String userId, String carVersionId) {
    }

    public sealed interface Response permits Success, Failure {
    }

    public record Success(String stockOrderId) implements Response {
    }

    public record Failure(String message) implements Response {
    }
}