CREATE TABLE stub_request
(
    id         SERIAL PRIMARY KEY,
    tag        VARCHAR(63) NOT NULL,
    value      TEXT        NOT NULL,
    created_at TIMESTAMP   NOT NULL
);