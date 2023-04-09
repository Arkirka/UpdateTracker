--liquibase formatted sql

--changeset vorobyov:create_chat_table
CREATE TABLE chat (
    id BIGINT NOT NULL PRIMARY KEY
);

--changeset vorobyov:create_link_table
CREATE TABLE link (
    id BIGINT NOT NULL PRIMARY KEY,
    link VARCHAR(255) NOT NULL,
    link_status ENUM('TRACKED', 'UNTRACKED') NOT NULL,
    chat_id BIGINT NOT NULL,
    FOREIGN KEY (chat_id) REFERENCES chat(id)
);