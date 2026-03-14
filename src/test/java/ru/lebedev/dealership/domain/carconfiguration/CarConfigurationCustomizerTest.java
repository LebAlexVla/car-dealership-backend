package ru.lebedev.dealership.domain.carconfiguration;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.detail.DetailType;
import ru.lebedev.dealership.domain.exceptions.IncompatibleDetailException;
import ru.lebedev.dealership.domain.exceptions.WrongOptionalDetailException;
import ru.lebedev.dealership.domain.exceptions.WrongRequiredDetailException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CarConfigurationCustomizerTest {

    @Test
    void shouldReplaceRequiredDetail() {

        DetailType engine = new DetailType("engine");

        Map<DetailType, Long> required = new HashMap<>();
        required.put(engine, 1L);

        CarConfigurationCustomizer customizer =
                new CarConfigurationCustomizer(1L, 10L, 100L, required);

        Detail detail = new Detail(
                2L,
                "V8",
                engine,
                null,
                Set.of(10L)
        );

        customizer.withRequiredDetail(detail);

        assertEquals(2L, required.get(engine));
    }

    @Test
    void shouldThrowIfRequiredDetailNotExpected() {

        DetailType engine = new DetailType("engine");
        DetailType wheels = new DetailType("wheels");

        Map<DetailType, Long> required = new HashMap<>();
        required.put(engine, 1L);

        CarConfigurationCustomizer customizer =
                new CarConfigurationCustomizer(1L, 10L, 100L, required);

        Detail detail = new Detail(
                2L,
                "Sport wheels",
                wheels,
                null,
                Set.of(10L)
        );

        assertThrows(
                WrongRequiredDetailException.class,
                () -> customizer.withRequiredDetail(detail)
        );
    }

    @Test
    void shouldThrowIfDetailIncompatible() {

        DetailType engine = new DetailType("engine");

        Map<DetailType, Long> required = new HashMap<>();
        required.put(engine, 1L);

        CarConfigurationCustomizer customizer =
                new CarConfigurationCustomizer(1L, 10L, 100L, required);

        Detail detail = new Detail(
                2L,
                "V8",
                engine,
                null,
                Set.of(99L)
        );

        assertThrows(
                IncompatibleDetailException.class,
                () -> customizer.withRequiredDetail(detail)
        );
    }

    @Test
    void shouldAddOptionalDetail() {

        DetailType engine = new DetailType("engine");
        DetailType wheels = new DetailType("wheels");

        Map<DetailType, Long> required = new HashMap<>();
        required.put(engine, 1L);

        CarConfigurationCustomizer customizer =
                new CarConfigurationCustomizer(1L, 10L, 100L, required);

        Detail optionalDetail = new Detail(
                5L,
                "Sport wheels",
                wheels,
                null,
                Set.of(10L)
        );

        customizer.withOptionalDetail(optionalDetail);

        CarConfiguration configuration = customizer.build(50L);

        assertTrue(configuration.detailsIds().contains(5L));
    }

    @Test
    void shouldThrowIfOptionalDetailIsRequired() {

        DetailType engine = new DetailType("engine");

        Map<DetailType, Long> required = new HashMap<>();
        required.put(engine, 1L);

        CarConfigurationCustomizer customizer =
                new CarConfigurationCustomizer(1L, 10L, 100L, required);

        Detail detail = new Detail(
                5L,
                "engine v8",
                engine,
                null,
                Set.of(10L)
        );

        assertThrows(
                WrongOptionalDetailException.class,
                () -> customizer.withOptionalDetail(detail)
        );
    }

    @Test
    void shouldThrowIfOptionalDetailIncompatible() {

        DetailType wheels = new DetailType("wheels");

        Map<DetailType, Long> required = new HashMap<>();

        CarConfigurationCustomizer customizer =
                new CarConfigurationCustomizer(1L, 10L, 100L, required);

        Detail detail = new Detail(
                5L,
                "sport wheels",
                wheels,
                null,
                Set.of(99L)
        );

        assertThrows(
                IncompatibleDetailException.class,
                () -> customizer.withOptionalDetail(detail)
        );
    }

    @Test
    void buildShouldContainRequiredAndOptionalDetails() {

        DetailType engine = new DetailType("engine");
        DetailType wheels = new DetailType("wheels");

        Map<DetailType, Long> required = new HashMap<>();
        required.put(engine, 1L);

        CarConfigurationCustomizer customizer =
                new CarConfigurationCustomizer(1L, 10L, 100L, required);

        Detail optionalDetail = new Detail(
                5L,
                "sport wheels",
                wheels,
                null,
                Set.of(10L)
        );

        customizer.withOptionalDetail(optionalDetail);

        CarConfiguration configuration = customizer.build(77L);

        assertTrue(configuration.detailsIds().contains(1L));
        assertTrue(configuration.detailsIds().contains(5L));
    }
}