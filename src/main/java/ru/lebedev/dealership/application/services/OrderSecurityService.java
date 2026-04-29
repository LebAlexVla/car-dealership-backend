package ru.lebedev.dealership.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebedev.dealership.infrastructure.persistence.repository.ConfiguredCarOrderRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.StockOrderRepository;

@Service("orderSecurityService")
public class OrderSecurityService {
    private final StockOrderRepository stockOrderRepository;
    private final ConfiguredCarOrderRepository configuredCarOrderRepository;
    private final CurrentUserService currentUserService;

    public OrderSecurityService(
            StockOrderRepository stockOrderRepository,
            ConfiguredCarOrderRepository configuredCarOrderRepository,
            CurrentUserService currentUserService
    ) {
        this.stockOrderRepository = stockOrderRepository;
        this.configuredCarOrderRepository = configuredCarOrderRepository;
        this.currentUserService = currentUserService;
    }

    @Transactional(readOnly = true)
    public boolean isStockOrderOwner(Long orderId) {
        Long currentUserId = currentUserService.getCurrentUserId();

        return stockOrderRepository.findById(orderId)
                .map(order -> order.getClient().getId().equals(currentUserId))
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public boolean isConfiguredOrderOwner(Long orderId) {
        Long currentUserId = currentUserService.getCurrentUserId();

        return configuredCarOrderRepository.findById(orderId)
                .map(order -> order.getClient().getId().equals(currentUserId))
                .orElse(false);
    }
}