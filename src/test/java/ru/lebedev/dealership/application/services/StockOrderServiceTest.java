package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockOrderServiceTest {

    @Mock
    private StockOrderRepository stockOrderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarVersionRepository carVersionRepository;

    @Mock
    private CurrentUserService currentUserService;

    private StockOrderService stockOrderService;

    @BeforeEach
    void setUp() {
        stockOrderService = new StockOrderService(
                stockOrderRepository,
                userRepository,
                carVersionRepository,
                currentUserService
        );
    }

    @Test
    void create_shouldCreateStockOrder() {
        User user = mock(User.class);
        CarVersion carVersion = mock(CarVersion.class);
        StockOrder savedOrder = mock(StockOrder.class);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carVersionRepository.findById(2L)).thenReturn(Optional.of(carVersion));
        when(stockOrderRepository.save(any(StockOrder.class))).thenReturn(savedOrder);
        when(savedOrder.getId()).thenReturn(100L);

        Long result = stockOrderService.create(1L, 2L);

        assertEquals(100L, result);
        verify(userRepository).findById(1L);
        verify(carVersionRepository).findById(2L);
        verify(stockOrderRepository).save(any(StockOrder.class));
    }

    @Test
    void create_shouldThrowWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> stockOrderService.create(1L, 2L));

        verify(stockOrderRepository, never()).save(any());
    }

    @Test
    void create_shouldThrowWhenCarVersionNotFound() {
        User user = mock(User.class);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carVersionRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(CarVersionNotFoundException.class, () -> stockOrderService.create(1L, 2L));

        verify(stockOrderRepository, never()).save(any());
    }

    @Test
    void findById_shouldReturnOrder() {
        StockOrder order = mock(StockOrder.class);

        when(stockOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<StockOrder> result = stockOrderService.findById(1L);

        assertEquals(Optional.of(order), result);
    }

    @Test
    void findByClientId_shouldReturnOrdersForAdmin() {
        StockOrder order = mock(StockOrder.class);

        when(currentUserService.hasRole("ADMIN")).thenReturn(true);
        when(stockOrderRepository.findByClientId(1L)).thenReturn(List.of(order));

        List<StockOrder> result = stockOrderService.findByClientId(1L);

        assertEquals(List.of(order), result);
        verify(stockOrderRepository).findByClientId(1L);
    }

    @Test
    void findByClientId_shouldReturnOrdersForManager() {
        StockOrder order = mock(StockOrder.class);

        when(currentUserService.hasRole("ADMIN")).thenReturn(false);
        when(currentUserService.hasRole("MANAGER")).thenReturn(true);
        when(stockOrderRepository.findByClientId(1L)).thenReturn(List.of(order));

        List<StockOrder> result = stockOrderService.findByClientId(1L);

        assertEquals(List.of(order), result);
        verify(stockOrderRepository).findByClientId(1L);
    }

    @Test
    void findByClientId_shouldReturnOrdersForCurrentUser() {
        StockOrder order = mock(StockOrder.class);

        when(currentUserService.hasRole("ADMIN")).thenReturn(false);
        when(currentUserService.hasRole("MANAGER")).thenReturn(false);
        when(currentUserService.getCurrentUserId()).thenReturn(1L);
        when(stockOrderRepository.findByClientId(1L)).thenReturn(List.of(order));

        List<StockOrder> result = stockOrderService.findByClientId(1L);

        assertEquals(List.of(order), result);
        verify(stockOrderRepository).findByClientId(1L);
    }

    @Test
    void findByClientId_shouldReturnEmptyListForForeignClientWhenUser() {
        when(currentUserService.hasRole("ADMIN")).thenReturn(false);
        when(currentUserService.hasRole("MANAGER")).thenReturn(false);
        when(currentUserService.getCurrentUserId()).thenReturn(1L);

        List<StockOrder> result = stockOrderService.findByClientId(99L);

        assertTrue(result.isEmpty());
        verify(stockOrderRepository, never()).findByClientId(anyLong());
    }

    @Test
    void findAll_shouldReturnAllOrdersForAdmin() {
        StockOrder order = mock(StockOrder.class);

        when(currentUserService.hasRole("ADMIN")).thenReturn(true);
        when(stockOrderRepository.findAll()).thenReturn(List.of(order));

        List<StockOrder> result = stockOrderService.findAll();

        assertEquals(List.of(order), result);
        verify(stockOrderRepository).findAll();
        verify(stockOrderRepository, never()).findByClientId(anyLong());
    }

    @Test
    void findAll_shouldReturnAllOrdersForManager() {
        StockOrder order = mock(StockOrder.class);

        when(currentUserService.hasRole("ADMIN")).thenReturn(false);
        when(currentUserService.hasRole("MANAGER")).thenReturn(true);
        when(stockOrderRepository.findAll()).thenReturn(List.of(order));

        List<StockOrder> result = stockOrderService.findAll();

        assertEquals(List.of(order), result);
        verify(stockOrderRepository).findAll();
        verify(stockOrderRepository, never()).findByClientId(anyLong());
    }

    @Test
    void findAll_shouldReturnOnlyCurrentUserOrdersForUser() {
        StockOrder order = mock(StockOrder.class);

        when(currentUserService.hasRole("ADMIN")).thenReturn(false);
        when(currentUserService.hasRole("MANAGER")).thenReturn(false);
        when(currentUserService.getCurrentUserId()).thenReturn(1L);
        when(stockOrderRepository.findByClientId(1L)).thenReturn(List.of(order));

        List<StockOrder> result = stockOrderService.findAll();

        assertEquals(List.of(order), result);
        verify(stockOrderRepository).findByClientId(1L);
        verify(stockOrderRepository, never()).findAll();
    }

    @Test
    void approveOrder_shouldApproveOrder() {
        StockOrder order = mock(StockOrder.class);

        when(stockOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        stockOrderService.approveOrder(1L);

        verify(order).approve();
    }

    @Test
    void approveOrder_shouldThrowWhenOrderNotFound() {
        when(stockOrderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StockOrderNotFoundException.class, () -> stockOrderService.approveOrder(1L));
    }

    @Test
    void payOrder_shouldPayOrder() {
        StockOrder order = mock(StockOrder.class);

        when(stockOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        stockOrderService.payOrder(1L);

        verify(order).pay();
    }

    @Test
    void completeOrder_shouldCompleteOrder() {
        StockOrder order = mock(StockOrder.class);

        when(stockOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        stockOrderService.completeOrder(1L);

        verify(order).complete();
    }

    @Test
    void cancelOrder_shouldCancelOrder() {
        StockOrder order = mock(StockOrder.class);

        when(stockOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        stockOrderService.cancelOrder(1L);

        verify(order).cancel();
    }

    @Test
    void deleteById_shouldDeleteOrder() {
        stockOrderService.deleteById(1L);

        verify(stockOrderRepository).deleteById(1L);
    }
}