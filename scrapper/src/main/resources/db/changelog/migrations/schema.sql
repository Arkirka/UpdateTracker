--liquibase formatted sql

--changeset vorobyov:create_chat_and_link_tables
CREATE TABLE chat (
    id SERIAL PRIMARY KEY
);

CREATE TABLE link (
    id SERIAL PRIMARY KEY,
    link VARCHAR(255) NOT NULL,
    chat_id BIGINT NOT NULL,
    FOREIGN KEY (chat_id) REFERENCES chat(id)
);
