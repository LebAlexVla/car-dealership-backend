package ru.lebedev.dealership.domain.order.stock;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.domain.exceptions.OrderStatusTransitionException;

import static org.junit.jupiter.api.Assertions.*;

class StockOrderTest {

    @Test
    void shouldCompleteHappyPath() {

        StockOrder order =
                new StockOrder(1L, 2L, 3L);

        order.approve();
        order.pay();
        order.complete();

        assertEquals(1L, order.getOrderId());
    }

    @Test
    void shouldThrowIfPayBeforeApprove() {

        StockOrder order =
                new StockOrder(1L, 2L, 3L);

        assertThrows(
                OrderStatusTransitionException.class,
                order::pay
        );
    }

    @Test
    void shouldCancelOrder() {

        StockOrder order =
                new StockOrder(1L, 2L, 3L);

        order.cancel();

        assertEquals(1L, order.getOrderId());
    }

    @Test
    void shouldThrowIfCompleteBeforePay() {

        StockOrder order =
                new StockOrder(1L,2L,3L);

        order.approve();

        assertThrows(
                OrderStatusTransitionException.class,
                order::complete
        );
    }
}