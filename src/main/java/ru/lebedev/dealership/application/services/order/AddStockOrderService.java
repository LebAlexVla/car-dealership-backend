package ru.lebedev.dealership.application.services.order;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.StockOrderRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.order.stockorder.AddStockOrderUseCase;
import ru.lebedev.dealership.application.contracts.order.stockorder.operations.AddStockOrder;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;
import ru.lebedev.dealership.domain.order.stock.StockOrder;
import ru.lebedev.dealership.domain.order.stock.StockOrderId;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.UUID;

public class AddStockOrderService implements AddStockOrderUseCase {
    private final Permission permission;

    private final UserRepository userRepository;
    private final StockOrderRepository stockOrderRepository;

    public AddStockOrderService(Permission permission, UserRepository userRepository, StockOrderRepository stockOrderRepository) {
        this.permission = permission;
        this.userRepository = userRepository;
        this.stockOrderRepository = stockOrderRepository;
    }

    @Override
    public AddStockOrder.Response add(AddStockOrder.Request request) {
        var userId = new UserId(UUID.fromString(request.userId()));
        User user = userRepository.findById(userId);
        if (!permission.check(user.type())) {
            return new AddStockOrder.Failure("The user doesn't have the permission to create stock order");
        }

        var stockOrderId = new StockOrderId(UUID.randomUUID());
        var carVersionId = new CarVersionId(UUID.fromString(request.carVersionId()));
        var stockOrder = new StockOrder(stockOrderId, userId, carVersionId);

        stockOrder = stockOrderRepository.save(stockOrder);

        return new AddStockOrder.Success(stockOrder.getOrderId().value().toString());
    }
}