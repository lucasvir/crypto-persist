CREATE TABLE users (
    id INT PRIMARY KEY,
    user_document VARCHAR(255) NOT NULL,
    credit_card_token VARCHAR(255) NOT NULL,
    value BIGINT NOT NULL
);