--liquibase formatted sql

--changeset lebedev:013-create-car-configuration-details
CREATE TABLE car_configuration_details
(
    car_configuration_id BIGINT NOT NULL,
    detail_id            BIGINT NOT NULL,

    PRIMARY KEY (car_configuration_id, detail_id),

    CONSTRAINT fk_car_configuration_details_config
        FOREIGN KEY (car_configuration_id) REFERENCES car_configuration (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_car_configuration_details_detail
        FOREIGN KEY (detail_id) REFERENCES detail (id)
            ON DELETE CASCADE
);

--rollback DROP TABLE car_configuration_details;