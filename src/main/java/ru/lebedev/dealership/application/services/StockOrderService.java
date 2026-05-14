package ru.lebedev.dealership.application.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final CurrentUserService currentUserService;

    public StockOrderService(
            StockOrderRepository stockOrderRepository,
            UserRepository userRepository,
            CarVersionRepository carVersionRepository,
            CurrentUserService currentUserService
    ) {
        this.stockOrderRepository = stockOrderRepository;
        this.userRepository = userRepository;
        this.carVersionRepository = carVersionRepository;
        this.currentUserService = currentUserService;
    }

    @Transactional
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Long create(Long clientId, Long carVersionId) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new UserNotFoundException(clientId));

        CarVersion carVersion = carVersionRepository.findById(carVersionId)
                .orElseThrow(() -> new CarVersionNotFoundException(carVersionId));

        StockOrder savedStockOrder = stockOrderRepository.save(new StockOrder(client, carVersion));

        return savedStockOrder.getId();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @orderSecurityService.isStockOrderOwner(#stockOrderId)")
    public Optional<StockOrder> findById(Long stockOrderId) {
        return stockOrderRepository.findById(stockOrderId);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public List<StockOrder> findByClientId(Long clientId) {
        if (currentUserService.hasRole("ADMIN") || currentUserService.hasRole("MANAGER")) {
            return stockOrderRepository.findByClientId(clientId);
        }

        Long currentUserId = currentUserService.getCurrentUserId();

        if (!currentUserId.equals(clientId)) {
            return List.of();
        }

        return stockOrderRepository.findByClientId(clientId);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public List<StockOrder> findAll() {
        if (currentUserService.hasRole("ADMIN") || currentUserService.hasRole("MANAGER")) {
            return stockOrderRepository.findAll();
        }

        return stockOrderRepository.findByClientId(currentUserService.getCurrentUserId());
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'WAREHOUSE_ADMIN', 'ADMIN')")
    public void approveOrder(Long stockOrderId) {
        StockOrder stockOrder = stockOrderRepository.findById(stockOrderId)
                .orElseThrow(() -> new StockOrderNotFoundException(stockOrderId));

        stockOrder.approve();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @orderSecurityService.isStockOrderOwner(#stockOrderId)")
    public void payOrder(Long stockOrderId) {
        StockOrder stockOrder = stockOrderRepository.findById(stockOrderId)
                .orElseThrow(() -> new StockOrderNotFoundException(stockOrderId));

        stockOrder.pay();
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public void completeOrder(Long stockOrderId) {
        StockOrder stockOrder = stockOrderRepository.findById(stockOrderId)
                .orElseThrow(() -> new StockOrderNotFoundException(stockOrderId));

        stockOrder.complete();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @orderSecurityService.isStockOrderOwner(#stockOrderId)")
    public void cancelOrder(Long stockOrderId) {
        StockOrder stockOrder = stockOrderRepository.findById(stockOrderId)
                .orElseThrow(() -> new StockOrderNotFoundException(stockOrderId));

        stockOrder.cancel();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long stockOrderId) {
        stockOrderRepository.deleteById(stockOrderId);
    }
}