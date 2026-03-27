package ru.lebedev.dealership.unit.domain.carconfiguration;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.detail.DetailType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CarConfigurationDefaulterTest {

    @Test
    void shouldAddRequiredDetail() {

        Map<DetailType, Long> required = new HashMap<>();

        CarConfigurationDefaulter defaulter =
                new CarConfigurationDefaulter(1L, 10L, required);

        DetailType type = new DetailType("spoiler");

        Detail detail = new Detail(
                5L,
                "gta",
                type,
                null,
                new HashSet<>()
        );

        defaulter.addRequiredDetail(detail);

        assertEquals(5L, required.get(type));
    }

    @Test
    void shouldRemoveRequiredDetail() {

        DetailType type = new DetailType("spoiler");

        Map<DetailType, Long> required = new HashMap<>();
        required.put(type, 5L);

        CarConfigurationDefaulter defaulter =
                new CarConfigurationDefaulter(1L, 10L, required);

        defaulter.removeRequiredDetail(type);

        assertFalse(required.containsKey(type));
    }

    @Test
    void shouldCreateCustomizer() {

        DetailType type = new DetailType("spoiler");

        Map<DetailType, Long> required = new HashMap<>();
        required.put(type, 5L);

        CarConfigurationDefaulter defaulter =
                new CarConfigurationDefaulter(1L, 10L, required);

        CarConfigurationCustomizer customizer =
                defaulter.create(100L, 200L);

        assertEquals(200L, customizer.getClientId());
        assertEquals(100L, customizer.getCustomCarConfigurationId());
    }

    @Test
    void createShouldCopyRequiredDetails() {

        DetailType engine = new DetailType("engine");

        Map<DetailType, Long> required = new HashMap<>();
        required.put(engine, 1L);

        CarConfigurationDefaulter defaulter =
                new CarConfigurationDefaulter(5L, 10L, required);

        CarConfigurationCustomizer customizer =
                defaulter.create(100L, 200L);

        required.clear();

        assertEquals(200L, customizer.getClientId());
    }
}