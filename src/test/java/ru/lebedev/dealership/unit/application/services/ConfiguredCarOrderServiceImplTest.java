package ru.lebedev.dealership.unit.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.ConfiguredCarOrderRepository;
import ru.lebedev.dealership.application.contracts.order.configuredcar.models.ConfiguredCarOrderInputDto;
import ru.lebedev.dealership.application.contracts.order.configuredcar.requests.AddConfigurationOrderRequest;
import ru.lebedev.dealership.application.contracts.order.configuredcar.requests.UpdateConfigurationOrderRequest;
import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConfiguredCarOrderServiceImplTest {

    private ConfiguredCarOrderRepository repository;
    private ConfiguredCarOrderServiceImpl service;

    @BeforeEach
    void setup() {
        repository = mock(ConfiguredCarOrderRepository.class);
        service = new ConfiguredCarOrderServiceImpl(repository);
    }

    @Test
    void addOrder_shouldSaveOrder() {

        ConfiguredCarOrderInputDto dto =
                new ConfiguredCarOrderInputDto(1L, 2L, 3L);

        AddConfigurationOrderRequest request =
                new AddConfigurationOrderRequest(dto);

        ConfiguredCarOrder saved =
                new ConfiguredCarOrder(10L, 1L, 2L, 3L);

        when(repository.save(any())).thenReturn(saved);

        Long result = service.addOrder(request);

        assertEquals(10L, result);
        verify(repository).save(any());
    }

    @Test
    void deleteOrder_shouldCallRepository() {

        UpdateConfigurationOrderRequest request =
                new UpdateConfigurationOrderRequest(5L);

        service.deleteOrder(request);

        verify(repository).delete(5L);
    }

    @Test
    void approveOrder_shouldApproveAndSave() {

        ConfiguredCarOrder order =
                new ConfiguredCarOrder(5L, 1L, 2L, 3L);

        when(repository.findById(5L)).thenReturn(order);

        UpdateConfigurationOrderRequest request =
                new UpdateConfigurationOrderRequest(5L);

        service.approveOrder(request);

        verify(repository).save(order);
    }

    @Test
    void payOrder_shouldPayAndSave() {

        ConfiguredCarOrder order =
                new ConfiguredCarOrder(5L, 1L, 2L, 3L);

        order.approve();

        when(repository.findById(5L)).thenReturn(order);

        UpdateConfigurationOrderRequest request =
                new UpdateConfigurationOrderRequest(5L);

        service.payOrder(request);

        verify(repository).save(order);
    }

    @Test
    void deliverOrder_shouldDeliver() {

        ConfiguredCarOrder order =
                new ConfiguredCarOrder(5L, 1L, 2L, 3L);

        order.approve();
        order.pay();

        when(repository.findById(5L)).thenReturn(order);

        service.deliverOrder(new UpdateConfigurationOrderRequest(5L));

        verify(repository).save(order);
    }

    @Test
    void completeOrder_shouldComplete() {

        ConfiguredCarOrder order =
                new ConfiguredCarOrder(5L, 1L, 2L, 3L);

        order.approve();
        order.pay();
        order.deliver();

        when(repository.findById(5L)).thenReturn(order);

        service.completeOrder(new UpdateConfigurationOrderRequest(5L));

        verify(repository).save(order);
    }

    @Test
    void cancelOrder_shouldCancel() {

        ConfiguredCarOrder order =
                new ConfiguredCarOrder(5L, 1L, 2L, 3L);

        when(repository.findById(5L)).thenReturn(order);

        service.cancelOrder(new UpdateConfigurationOrderRequest(5L));

        verify(repository).save(order);
    }
}