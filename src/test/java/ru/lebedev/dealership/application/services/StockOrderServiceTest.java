package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lebedev.dealership.application.exceptions.CarVersionNotFoundException;
import ru.lebedev.dealership.application.exceptions.StockOrderNotFoundException;
import ru.lebedev.dealership.application.exceptions.UserNotFoundException;
import ru.lebedev.dealership.domain.order.stock.StockOrder;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarVersionRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.StockOrderRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.UserRepository;
import ru.lebedev.dealership.support.TestDataFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockOrderServiceTest {

    @Mock
    private StockOrderRepository stockOrderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarVersionRepository carVersionRepository;

    @Test
    void shouldCreateFindAndProcessStockOrder() {
        StockOrderService service = new StockOrderService(stockOrderRepository, userRepository, carVersionRepository);
        var user = TestDataFactory.user(1L);
        var car = TestDataFactory.carVersion(2L);
        var order = new StockOrder(user, car);
        TestDataFactory.setId(order, 3L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carVersionRepository.findById(2L)).thenReturn(Optional.of(car));
        when(stockOrderRepository.save(any(StockOrder.class))).thenReturn(order);
        when(stockOrderRepository.findById(3L)).thenReturn(Optional.of(order));
        when(stockOrderRepository.findByClientId(1L)).thenReturn(Optional.of(order));
        when(stockOrderRepository.findAll()).thenReturn(List.of(order));

        assertEquals(3L, service.create(1L, 2L));
        assertEquals(Optional.of(order), service.findById(3L));
        assertEquals(Optional.of(order), service.findByClientId(1L));
        assertEquals(1, service.findAll().size());

        service.approveOrder(3L);
        service.payOrder(3L);
        service.completeOrder(3L);
        service.cancelOrder(3L);
        service.deleteById(3L);

        verify(stockOrderRepository).deleteById(3L);
    }

    @Test
    void shouldThrowForMissingCreateDependenciesOrOrder() {
        StockOrderService service = new StockOrderService(stockOrderRepository, userRepository, carVersionRepository);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.create(1L, 2L));

        when(userRepository.findById(1L)).thenReturn(Optional.of(TestDataFactory.user(1L)));
        when(carVersionRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(CarVersionNotFoundException.class, () -> service.create(1L, 2L));

        when(stockOrderRepository.findById(3L)).thenReturn(Optional.empty());
        assertThrows(StockOrderNotFoundException.class, () -> service.approveOrder(3L));
        assertThrows(StockOrderNotFoundException.class, () -> service.payOrder(3L));
        assertThrows(StockOrderNotFoundException.class, () -> service.completeOrder(3L));
        assertThrows(StockOrderNotFoundException.class, () -> service.cancelOrder(3L));
    }
}
