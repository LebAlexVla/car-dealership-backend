package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lebedev.dealership.application.exceptions.CarConfigurationNotFoundException;
import ru.lebedev.dealership.application.exceptions.ConfiguredCarOrderNotFoundException;
import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;
import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrder;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarConfigurationRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.ConfiguredCarOrderRepository;
import ru.lebedev.dealership.support.TestDataFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfiguredCarOrderServiceTest {

    @Mock
    private ConfiguredCarOrderRepository orderRepository;

    @Mock
    private CarConfigurationRepository configurationRepository;

    @Test
    void shouldCreateFindAndProcessConfiguredOrder() {
        ConfiguredCarOrderService service = new ConfiguredCarOrderService(orderRepository, configurationRepository);
        var configuration = new CarConfiguration(TestDataFactory.user(1L), TestDataFactory.carVersion(2L), Set.of());
        TestDataFactory.setId(configuration, 10L);
        var order = new ConfiguredCarOrder(configuration);
        TestDataFactory.setId(order, 20L);

        when(configurationRepository.findById(10L)).thenReturn(Optional.of(configuration));
        when(orderRepository.save(any(ConfiguredCarOrder.class))).thenReturn(order);
        when(orderRepository.findById(20L)).thenReturn(Optional.of(order));
        when(orderRepository.findByConfigurationClientId(1L)).thenReturn(Optional.of(order));
        when(orderRepository.findAll()).thenReturn(List.of(order));

        assertEquals(20L, service.create(10L));
        assertEquals(Optional.of(order), service.findById(20L));
        assertEquals(Optional.of(order), service.findByClientId(1L));
        assertEquals(1, service.findAll().size());

        service.approveOrder(20L);
        service.payOrder(20L);
        service.deliverOrder(20L);
        service.completeOrder(20L);
        service.cancelOrder(20L);
        service.deleteById(20L);

        verify(orderRepository).deleteById(20L);
    }

    @Test
    void shouldThrowForMissingConfigurationOrOrder() {
        ConfiguredCarOrderService service = new ConfiguredCarOrderService(orderRepository, configurationRepository);

        when(configurationRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(CarConfigurationNotFoundException.class, () -> service.create(10L));

        when(orderRepository.findById(20L)).thenReturn(Optional.empty());
        assertThrows(ConfiguredCarOrderNotFoundException.class, () -> service.approveOrder(20L));
        assertThrows(ConfiguredCarOrderNotFoundException.class, () -> service.payOrder(20L));
        assertThrows(ConfiguredCarOrderNotFoundException.class, () -> service.deliverOrder(20L));
        assertThrows(ConfiguredCarOrderNotFoundException.class, () -> service.completeOrder(20L));
        assertThrows(ConfiguredCarOrderNotFoundException.class, () -> service.cancelOrder(20L));
    }
}
