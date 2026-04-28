--liquibase formatted sql

--changeset lebedev:017-add-user-keycloak-id
ALTER TABLE users
    ADD COLUMN keycloak_id VARCHAR(100);

ALTER TABLE users
    ADD CONSTRAINT uq_users_keycloak_id UNIQUE (keycloak_id);

--rollback ALTER TABLE users DROP CONSTRAINT uq_users_keycloak_id;
--rollback ALTER TABLE users DROP COLUMN keycloak_id;