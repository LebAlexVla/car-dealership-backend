--liquibase formatted sql

--changeset lebedev:010-create-configuration-customizer-required-details
CREATE TABLE configuration_customizer_required_details
(
    car_configuration_customizer_id BIGINT NOT NULL,
    detail_id                       BIGINT NOT NULL,

    PRIMARY KEY (car_configuration_customizer_id, detail_id),

    CONSTRAINT fk_config_customizer_req_details_config
        FOREIGN KEY (car_configuration_customizer_id) REFERENCES car_configuration_customizer (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_config_customizer_req_details_detail
        FOREIGN KEY (detail_id) REFERENCES detail (id)
            ON DELETE CASCADE
);

--rollback DROP TABLE configuration_customizer_required_details