--liquibase formatted sql

--changeset vorobyov:create_chat_and_link_tables
CREATE TABLE chat (
    id BIGINT NOT NULL PRIMARY KEY
);

CREATE TABLE link (
    id BIGINT NOT NULL PRIMARY KEY,
    link VARCHAR(255) NOT NULL,
    link_status VARCHAR(10) NOT NULL CHECK (link_status IN ('TRACKED', 'UNTRACKED')),
    chat_id BIGINT NOT NULL,
    FOREIGN KEY (chat_id) REFERENCES chat(id)
);
