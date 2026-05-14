package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrder;
import ru.lebedev.dealership.domain.order.stock.StockOrder;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.infrastructure.persistence.repository.ConfiguredCarOrderRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.StockOrderRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderSecurityServiceTest {

    @Mock
    private StockOrderRepository stockOrderRepository;

    @Mock
    private ConfiguredCarOrderRepository configuredCarOrderRepository;

    @Mock
    private CurrentUserService currentUserService;

    @InjectMocks
    private OrderSecurityService orderSecurityService;

    @Test
    void isStockOrderOwner_shouldReturnTrueWhenCurrentUserOwnsOrder() {
        User client = mock(User.class);
        StockOrder order = mock(StockOrder.class);

        when(currentUserService.getCurrentUserId()).thenReturn(10L);
        when(order.getClient()).thenReturn(client);
        when(client.getId()).thenReturn(10L);
        when(stockOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertTrue(orderSecurityService.isStockOrderOwner(1L));
    }

    @Test
    void isStockOrderOwner_shouldReturnFalseWhenCurrentUserDoesNotOwnOrder() {
        User client = mock(User.class);
        StockOrder order = mock(StockOrder.class);

        when(currentUserService.getCurrentUserId()).thenReturn(10L);
        when(order.getClient()).thenReturn(client);
        when(client.getId()).thenReturn(99L);
        when(stockOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertFalse(orderSecurityService.isStockOrderOwner(1L));
    }

    @Test
    void isStockOrderOwner_shouldReturnFalseWhenOrderNotFound() {
        when(currentUserService.getCurrentUserId()).thenReturn(10L);
        when(stockOrderRepository.findById(1L)).thenReturn(Optional.empty());

        assertFalse(orderSecurityService.isStockOrderOwner(1L));
    }

    @Test
    void isConfiguredOrderOwner_shouldReturnTrueWhenCurrentUserOwnsOrder() {
        User client = mock(User.class);
        ConfiguredCarOrder order = mock(ConfiguredCarOrder.class);

        when(currentUserService.getCurrentUserId()).thenReturn(10L);
        when(order.getClient()).thenReturn(client);
        when(client.getId()).thenReturn(10L);
        when(configuredCarOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertTrue(orderSecurityService.isConfiguredOrderOwner(1L));
    }

    @Test
    void isConfiguredOrderOwner_shouldReturnFalseWhenCurrentUserDoesNotOwnOrder() {
        User client = mock(User.class);
        ConfiguredCarOrder order = mock(ConfiguredCarOrder.class);

        when(currentUserService.getCurrentUserId()).thenReturn(10L);
        when(order.getClient()).thenReturn(client);
        when(client.getId()).thenReturn(99L);
        when(configuredCarOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertFalse(orderSecurityService.isConfiguredOrderOwner(1L));
    }

    @Test
    void isConfiguredOrderOwner_shouldReturnFalseWhenOrderNotFound() {
        when(currentUserService.getCurrentUserId()).thenReturn(10L);
        when(configuredCarOrderRepository.findById(1L)).thenReturn(Optional.empty());

        assertFalse(orderSecurityService.isConfiguredOrderOwner(1L));
    }
}