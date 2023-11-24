CREATE TABLE users (
    id INT PRIMARY KEY,
    userDocument VARCHAR(255) NOT NULL,
    creditCardToken VARCHAR(255) NOT NULL,
    value BIGINT NOT NULL
);