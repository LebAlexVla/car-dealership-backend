package ru.lebedev.dealership.application.services;

import org.springframework.stereotype.Service;
import ru.lebedev.dealership.application.exceptions.CarVersionNotFoundException;
import ru.lebedev.dealership.application.exceptions.StockOrderNotFoundException;
import ru.lebedev.dealership.application.exceptions.UserNotFoundException;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.order.stock.StockOrder;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarVersionRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.StockOrderRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StockOrderService {
    private final StockOrderRepository stockOrderRepository;
    private final UserRepository userRepository;
    private final CarVersionRepository carVersionRepository;

    public StockOrderService(StockOrderRepository stockOrderRepository, UserRepository userRepository, CarVersionRepository carVersionRepository) {
        this.stockOrderRepository = stockOrderRepository;
        this.userRepository = userRepository;
        this.carVersionRepository = carVersionRepository;
    }

    public Long create(Long clientId, Long carVersionId) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new UserNotFoundException(clientId));
        CarVersion carVersion = carVersionRepository.findById(carVersionId)
                .orElseThrow(() -> new CarVersionNotFoundException(carVersionId));
        StockOrder savedStockOrder = stockOrderRepository.save(new StockOrder(client, carVersion));

        return savedStockOrder.getId();
    }

    public Optional<StockOrder> findById(Long stockOrderId) {
        return stockOrderRepository.findById(stockOrderId);
    }

    public Optional<StockOrder> findByClientId(Long clientId) {
        return stockOrderRepository.findByClientId(clientId);
    }

    public List<StockOrder> findAll() {
        return stockOrderRepository.findAll();
    }

    public void approveOrder(Long stockOrderId) {
        StockOrder stockOrder = stockOrderRepository.findById(stockOrderId)
                .orElseThrow(() -> new StockOrderNotFoundException(stockOrderId));
        stockOrder.approve();
    }

    public void payOrder(Long stockOrderId) {
        StockOrder stockOrder = stockOrderRepository.findById(stockOrderId)
                .orElseThrow(() -> new StockOrderNotFoundException(stockOrderId));
        stockOrder.pay();
    }

    public void completeOrder(Long stockOrderId) {
        StockOrder stockOrder = stockOrderRepository.findById(stockOrderId)
                .orElseThrow(() -> new StockOrderNotFoundException(stockOrderId));
        stockOrder.complete();
    }

    public void cancelOrder(Long stockOrderId) {
        StockOrder stockOrder = stockOrderRepository.findById(stockOrderId)
                .orElseThrow(() -> new StockOrderNotFoundException(stockOrderId));
        stockOrder.cancel();
    }

    public void deleteById(Long stockOrderId) {
        stockOrderRepository.deleteById(stockOrderId);
    }
}