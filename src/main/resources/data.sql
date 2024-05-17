-- Insert data into accounts

-- Insert data into users
-- Using IDENTITY() to fetch the most recently generated ID
INSERT INTO users (name, last_name, email, password, address)
VALUES ('Marie', 'Doe', 'marie.doe@example.com', 'password123!', '123 Main St'),
       ('John', 'Doe', 'john.doe@example.com', 'password123!', '123 Main St');

INSERT INTO accounts (cvu, wallet_address, reputation, user_id)
VALUES ('1234567890123456789034', 'FBCDEFGH', 0, 1),
       ('1234567890123456789012', 'ABCDEFGH', 0, 2);

UPDATE users SET account_id = 1 WHERE id = 1;
UPDATE users SET account_id = 2 WHERE id = 2;

-- Insert data into cryptocurrencies
INSERT INTO cryptocurrencies (symbol, price, last_update_date_and_time)
VALUES (1, 45000.0, CURRENT_TIMESTAMP);

-- Insert data into operation_intents
INSERT INTO operation_intents (symbol, nominal_quantity, nominal_price, local_price, operation, account_id, status, created_date)
VALUES (1, 2, 0.69400, 600000.0, 0, 1, 0, CURRENT_TIMESTAMP),
       (3, 5, 600.0, 800000.0, 1, 2, 0, CURRENT_TIMESTAMP);

-- Insert data into transactions
-- INSERT INTO transactions (intention_id, seller_id, buyer_id, status, initiated_at)
-- VALUES (1, 2, 1, 2, CURRENT_TIMESTAMP),
--        (2, 1, 2, 2, CURRENT_TIMESTAMP);
