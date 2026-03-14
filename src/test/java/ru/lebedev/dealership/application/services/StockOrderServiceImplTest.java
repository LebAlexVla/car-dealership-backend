package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.StockOrderRepository;
import ru.lebedev.dealership.application.contracts.order.stock.models.StockOrderInputDto;
import ru.lebedev.dealership.application.contracts.order.stock.requests.AddStockOrderRequest;
import ru.lebedev.dealership.application.contracts.order.stock.requests.UpdateStockOrderRequest;
import ru.lebedev.dealership.domain.order.stock.StockOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StockOrderServiceImplTest {

    private StockOrderRepository repository;
    private StockOrderServiceImpl service;

    @BeforeEach
    void setup() {
        repository = mock(StockOrderRepository.class);
        service = new StockOrderServiceImpl(repository);
    }

    @Test
    void addOrder_shouldSaveOrder() {

        StockOrderInputDto dto =
                new StockOrderInputDto(1L,2L);

        AddStockOrderRequest request =
                new AddStockOrderRequest(dto);

        StockOrder saved = new StockOrder(10L,1L,2L);

        when(repository.save(any())).thenReturn(saved);

        Long result = service.addOrder(request);

        assertEquals(10L, result);
        verify(repository).save(any());
    }

    @Test
    void deleteOrder_shouldDelete() {

        service.deleteOrder(new UpdateStockOrderRequest(5L));

        verify(repository).delete(5L);
    }

    @Test
    void approveOrder_shouldApprove() {

        StockOrder order = new StockOrder(5L,1L,2L);

        when(repository.findById(5L)).thenReturn(order);

        service.approveOrder(new UpdateStockOrderRequest(5L));

        verify(repository).save(order);
    }

    @Test
    void payOrder_shouldPay() {

        StockOrder order = new StockOrder(5L,1L,2L);
        order.approve();

        when(repository.findById(5L)).thenReturn(order);

        service.payOrder(new UpdateStockOrderRequest(5L));

        verify(repository).save(order);
    }

    @Test
    void completeOrder_shouldComplete() {

        StockOrder order = new StockOrder(5L,1L,2L);
        order.approve();
        order.pay();

        when(repository.findById(5L)).thenReturn(order);

        service.completeOrder(new UpdateStockOrderRequest(5L));

        verify(repository).save(order);
    }

    @Test
    void cancelOrder_shouldCancel() {

        StockOrder order = new StockOrder(5L,1L,2L);

        when(repository.findById(5L)).thenReturn(order);

        service.cancelOrder(new UpdateStockOrderRequest(5L));

        verify(repository).save(order);
    }
}