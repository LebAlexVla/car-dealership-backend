--liquibase formatted sql

--changeset lebedev:008-create-configuration-defaulter-required-details
CREATE TABLE configuration_defaulter_required_details
(
    car_configuration_defaulter_id BIGINT NOT NULL,
    detail_id                      BIGINT NOT NULL,

    CONSTRAINT fk_config_defaulter_details_config
        FOREIGN KEY (car_configuration_defaulter_id) REFERENCES car_configuration_defaulter (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_config_defaulter_details_detail
        FOREIGN KEY (detail_id) REFERENCES detail (id)
            ON DELETE CASCADE
);
--rollback DROP TABLE configuration_defaulter_required_details