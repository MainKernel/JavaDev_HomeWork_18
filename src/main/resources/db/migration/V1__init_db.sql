CREATE TABLE IF NOT EXISTS users
(
    id                         BIGSERIAL PRIMARY KEY,
    username                   VARCHAR(64)  NOT NULL UNIQUE,
    email                      VARCHAR(128) NOT NULL UNIQUE,
    password                   VARCHAR(512) NOT NULL,
    role                       VARCHAR(16) DEFAULT 'ROLE_USER',
    is_account_non_expired     BOOLEAN     DEFAULT TRUE,
    is_account_non_locked      BOOLEAN     DEFAULT TRUE,
    is_credentials_non_expired BOOLEAN     DEFAULT TRUE,
    is_enabled                 BOOLEAN     DEFAULT TRUE
);



CREATE TABLE IF NOT EXISTS notes
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    INTEGER REFERENCES users (id),
    title      VARCHAR(256)  NOT NULL,
    content    VARCHAR(1024) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);
