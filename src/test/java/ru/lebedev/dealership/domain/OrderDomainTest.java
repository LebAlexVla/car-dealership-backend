package ru.lebedev.dealership.domain;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;
import ru.lebedev.dealership.domain.exceptions.OrderStatusTransitionException;
import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrder;
import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrderStatus;
import ru.lebedev.dealership.domain.order.stock.StockOrder;
import ru.lebedev.dealership.domain.order.stock.StockOrderStatus;
import ru.lebedev.dealership.support.TestDataFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderDomainTest {

    @Test
    void stockOrderShouldFollowHappyPathAndAllowCancel() {
        StockOrder order = new StockOrder(TestDataFactory.user(1L), TestDataFactory.carVersion(1L));

        order.approve();
        assertEquals(StockOrderStatus.WAITING_FOR_PAYMENT, order.getStatus());

        order.pay();
        assertEquals(StockOrderStatus.READY_FOR_PICK_UP, order.getStatus());

        order.complete();
        assertEquals(StockOrderStatus.COMPLETED, order.getStatus());

        order.cancel();
        assertEquals(StockOrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void stockOrderShouldRejectIllegalTransition() {
        StockOrder order = new StockOrder(TestDataFactory.user(1L), TestDataFactory.carVersion(1L));

        assertThrows(OrderStatusTransitionException.class, order::pay);
    }

    @Test
    void configuredOrderShouldFollowHappyPathAndExposeConfigurationData() {
        CarConfiguration configuration = new CarConfiguration(
                TestDataFactory.user(1L),
                TestDataFactory.carVersion(1L),
                Set.of()
        );
        ConfiguredCarOrder order = new ConfiguredCarOrder(configuration);

        assertNotNull(order.getClient());
        assertNotNull(order.getCarVersion());
        assertEquals(configuration, order.getConfiguration());

        order.approve();
        assertEquals(ConfiguredCarOrderStatus.WAITING_FOR_PAYMENT, order.getStatus());

        order.pay();
        assertEquals(ConfiguredCarOrderStatus.AWAITING_DELIVERY, order.getStatus());

        order.deliver();
        assertEquals(ConfiguredCarOrderStatus.READY_FOR_PICK_UP, order.getStatus());

        order.complete();
        assertEquals(ConfiguredCarOrderStatus.COMPLETED, order.getStatus());

        order.cancel();
        assertEquals(ConfiguredCarOrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void configuredOrderShouldRejectIllegalTransition() {
        ConfiguredCarOrder order = new ConfiguredCarOrder(
                new CarConfiguration(TestDataFactory.user(1L), TestDataFactory.carVersion(1L), Set.of())
        );

        assertThrows(OrderStatusTransitionException.class, order::deliver);
    }
}
