package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lebedev.dealership.application.exceptions.CarConfigurationNotFoundException;
import ru.lebedev.dealership.application.exceptions.ConfiguredCarOrderNotFoundException;
import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;
import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrder;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarConfigurationRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.ConfiguredCarOrderRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfiguredCarOrderServiceTest {

    @Mock
    private ConfiguredCarOrderRepository configuredCarOrderRepository;

    @Mock
    private CarConfigurationRepository carConfigurationRepository;

    @Mock
    private CurrentUserService currentUserService;

    private ConfiguredCarOrderService configuredCarOrderService;

    @BeforeEach
    void setUp() {
        configuredCarOrderService = new ConfiguredCarOrderService(
                configuredCarOrderRepository,
                carConfigurationRepository,
                currentUserService
        );
    }

    @Test
    void create_shouldCreateConfiguredCarOrderForAdmin() {
        CarConfiguration configuration = mock(CarConfiguration.class);
        ConfiguredCarOrder savedOrder = mock(ConfiguredCarOrder.class);

        when(currentUserService.hasRole("ADMIN")).thenReturn(true);
        when(carConfigurationRepository.findById(1L)).thenReturn(Optional.of(configuration));
        when(configuredCarOrderRepository.save(any(ConfiguredCarOrder.class))).thenReturn(savedOrder);
        when(savedOrder.getId()).thenReturn(100L);

        Long result = configuredCarOrderService.create(1L);

        assertEquals(100L, result);
        verify(configuredCarOrderRepository).save(any(ConfiguredCarOrder.class));
    }

    @Test
    void create_shouldCreateConfiguredCarOrderForOwner() {
        CarConfiguration configuration = mock(CarConfiguration.class);
        User client = mock(User.class);
        ConfiguredCarOrder savedOrder = mock(ConfiguredCarOrder.class);

        when(currentUserService.hasRole("ADMIN")).thenReturn(false);
        when(currentUserService.getCurrentUserId()).thenReturn(10L);
        when(configuration.getClient()).thenReturn(client);
        when(client.getId()).thenReturn(10L);
        when(carConfigurationRepository.findById(1L)).thenReturn(Optional.of(configuration));
        when(configuredCarOrderRepository.save(any(ConfiguredCarOrder.class))).thenReturn(savedOrder);
        when(savedOrder.getId()).thenReturn(100L);

        Long result = configuredCarOrderService.create(1L);

        assertEquals(100L, result);
        verify(configuredCarOrderRepository).save(any(ConfiguredCarOrder.class));
    }

    @Test
    void create_shouldThrowWhenConfigurationNotFound() {
        when(carConfigurationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CarConfigurationNotFoundException.class, () -> configuredCarOrderService.create(1L));

        verify(configuredCarOrderRepository, never()).save(any());
    }

    @Test
    void create_shouldThrowWhenConfigurationBelongsToAnotherUser() {
        CarConfiguration configuration = mock(CarConfiguration.class);
        User client = mock(User.class);

        when(currentUserService.hasRole("ADMIN")).thenReturn(false);
        when(currentUserService.getCurrentUserId()).thenReturn(10L);
        when(configuration.getClient()).thenReturn(client);
        when(client.getId()).thenReturn(99L);
        when(carConfigurationRepository.findById(1L)).thenReturn(Optional.of(configuration));

        assertThrows(CarConfigurationNotFoundException.class, () -> configuredCarOrderService.create(1L));

        verify(configuredCarOrderRepository, never()).save(any());
    }

    @Test
    void findById_shouldReturnOrder() {
        ConfiguredCarOrder order = mock(ConfiguredCarOrder.class);

        when(configuredCarOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<ConfiguredCarOrder> result = configuredCarOrderService.findById(1L);

        assertEquals(Optional.of(order), result);
    }

    @Test
    void findByClientId_shouldReturnOrdersForManager() {
        ConfiguredCarOrder order = mock(ConfiguredCarOrder.class);

        when(currentUserService.hasRole("ADMIN")).thenReturn(false);
        when(currentUserService.hasRole("MANAGER")).thenReturn(true);
        when(configuredCarOrderRepository.findByConfigurationClientId(1L)).thenReturn(List.of(order));

        List<ConfiguredCarOrder> result = configuredCarOrderService.findByClientId(1L);

        assertEquals(List.of(order), result);
    }

    @Test
    void findByClientId_shouldReturnEmptyListForForeignClientWhenUser() {
        when(currentUserService.hasRole("ADMIN")).thenReturn(false);
        when(currentUserService.hasRole("MANAGER")).thenReturn(false);
        when(currentUserService.getCurrentUserId()).thenReturn(10L);

        List<ConfiguredCarOrder> result = configuredCarOrderService.findByClientId(99L);

        assertTrue(result.isEmpty());
        verify(configuredCarOrderRepository, never()).findByConfigurationClientId(anyLong());
    }

    @Test
    void findAll_shouldReturnAllOrdersForAdmin() {
        ConfiguredCarOrder order = mock(ConfiguredCarOrder.class);

        when(currentUserService.hasRole("ADMIN")).thenReturn(true);
        when(configuredCarOrderRepository.findAll()).thenReturn(List.of(order));

        List<ConfiguredCarOrder> result = configuredCarOrderService.findAll();

        assertEquals(List.of(order), result);
        verify(configuredCarOrderRepository).findAll();
    }

    @Test
    void findAll_shouldReturnOnlyCurrentUserOrdersForUser() {
        ConfiguredCarOrder order = mock(ConfiguredCarOrder.class);

        when(currentUserService.hasRole("ADMIN")).thenReturn(false);
        when(currentUserService.hasRole("MANAGER")).thenReturn(false);
        when(currentUserService.getCurrentUserId()).thenReturn(10L);
        when(configuredCarOrderRepository.findByConfigurationClientId(10L)).thenReturn(List.of(order));

        List<ConfiguredCarOrder> result = configuredCarOrderService.findAll();

        assertEquals(List.of(order), result);
        verify(configuredCarOrderRepository).findByConfigurationClientId(10L);
        verify(configuredCarOrderRepository, never()).findAll();
    }

    @Test
    void approveOrder_shouldApproveOrder() {
        ConfiguredCarOrder order = mock(ConfiguredCarOrder.class);

        when(configuredCarOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        configuredCarOrderService.approveOrder(1L);

        verify(order).approve();
    }

    @Test
    void approveOrder_shouldThrowWhenOrderNotFound() {
        when(configuredCarOrderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ConfiguredCarOrderNotFoundException.class, () -> configuredCarOrderService.approveOrder(1L));
    }

    @Test
    void payOrder_shouldPayOrder() {
        ConfiguredCarOrder order = mock(ConfiguredCarOrder.class);

        when(configuredCarOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        configuredCarOrderService.payOrder(1L);

        verify(order).pay();
    }

    @Test
    void deliverOrder_shouldDeliverOrder() {
        ConfiguredCarOrder order = mock(ConfiguredCarOrder.class);

        when(configuredCarOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        configuredCarOrderService.deliverOrder(1L);

        verify(order).deliver();
    }

    @Test
    void completeOrder_shouldCompleteOrder() {
        ConfiguredCarOrder order = mock(ConfiguredCarOrder.class);

        when(configuredCarOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        configuredCarOrderService.completeOrder(1L);

        verify(order).complete();
    }

    @Test
    void cancelOrder_shouldCancelOrder() {
        ConfiguredCarOrder order = mock(ConfiguredCarOrder.class);

        when(configuredCarOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        configuredCarOrderService.cancelOrder(1L);

        verify(order).cancel();
    }

    @Test
    void deleteById_shouldDeleteOrder() {
        configuredCarOrderService.deleteById(1L);

        verify(configuredCarOrderRepository).deleteById(1L);
    }
}