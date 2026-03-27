package ru.lebedev.dealership.unit.domain.order.configuredcar;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.domain.exceptions.OrderStatusTransitionException;

import static org.junit.jupiter.api.Assertions.*;

class ConfiguredCarOrderTest {

    @Test
    void shouldCompleteHappyPath() {

        ConfiguredCarOrder order =
                new ConfiguredCarOrder(1L, 2L, 3L, 4L);

        order.approve();
        order.pay();
        order.deliver();
        order.complete();

        assertEquals(1L, order.getOrderId());
    }

    @Test
    void shouldThrowIfPayBeforeApprove() {

        ConfiguredCarOrder order =
                new ConfiguredCarOrder(1L, 2L, 3L, 4L);

        assertThrows(
                OrderStatusTransitionException.class,
                order::pay
        );
    }

    @Test
    void shouldAllowCancel() {

        ConfiguredCarOrder order =
                new ConfiguredCarOrder(1L, 2L, 3L, 4L);

        order.cancel();

        assertEquals(1L, order.getOrderId());
    }

    @Test
    void shouldThrowIfDeliverBeforePay() {

        ConfiguredCarOrder order =
                new ConfiguredCarOrder(1L,2L,3L,4L);

        order.approve();

        assertThrows(
                OrderStatusTransitionException.class,
                order::deliver
        );
    }
}