INSERT INTO accounts (cvu, WALLET_ADDRESS, reputation)
VALUES ('1234567890123456789012', 'ABCDEFGH', 10);

INSERT INTO users (name, LAST_NAME, email, password, address, account_id)
VALUES ('John', 'Doe', 'john.doe@example.com', 'password123!', '123 Main St', 1);

INSERT INTO cryptocurrencies (SYMBOL, PRICE, LAST_UPDATE_DATE_AND_TIME) VALUES (1, 45000.0, CURRENT_TIMESTAMP);
