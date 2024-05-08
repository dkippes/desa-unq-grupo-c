
INSERT INTO accounts (id, cvu, wallet_address, reputation)
VALUES (
           2,  -- id
           '1234567890123456789034',  -- cvu
           'FBCDEFGH',  -- WALLET_ADDRESS
           0  -- reputation
       );

INSERT INTO accounts (id, cvu, wallet_address, reputation)
VALUES (
           1,  -- id
           '1234567890123456789012',  -- cvu
           'ABCDEFGH',  -- WALLET_ADDRESS
           0  -- reputation
       );

INSERT INTO users (name, last_name, email, password, address, account_id)
VALUES (
           'Marie',  -- name
           'Doe',  -- LAST_NAME
           'marie.doe@example.com',  -- email
           'password123!',  -- password
           '123 Main St',  -- address
           2  -- account_id
       );

INSERT INTO users (name, LAST_NAME, email, password, address, account_id)
VALUES (
           'John',  -- name
           'Doe',  -- LAST_NAME
           'john.doe@example.com',  -- email
           'password123!',  -- password
           '123 Main St',  -- address
           1  -- account_id
       );

INSERT INTO cryptocurrencies (symbol, price, last_update_date_and_time)
VALUES (
           1,  -- SYMBOL
           45000.0,  -- PRICE
           CURRENT_TIMESTAMP
       );


INSERT INTO operation_intents (id, symbol, nominal_quantity, nominal_price, local_price, operation, account_id, status)
VALUES (
           1,  -- id
           1,  -- symbol
           2,  -- nominalQuantity
           300.0,  -- nominalPrice
           600000.0,  -- localPrice
           0,  -- operation
           1,  -- account_id
           0  -- status
       );

INSERT INTO operation_intents (id, symbol, nominal_quantity, nominal_price, local_price, operation, account_id, status)
VALUES (
           2,  -- id
           3,  -- symbol
           5,  -- nominalQuantity
           600.0,  -- nominalPrice
           800000.0,  -- localPrice
           1,  -- operation
           1,  -- account_id
           0  -- status
       );

INSERT INTO transactions (intention_id, seller_id, buyer_id, status, initiated_at)
VALUES (
           1,  -- intention_id (debe corresponder a un registro válido en operation_intents)
           2,  -- seller_id (debe corresponder a un registro válido en accounts)
           1,  -- buyer_id (debe corresponder a un registro válido en accounts)
           2,  -- status (suponiendo que 3 es un valor válido para el estado)
           CURRENT_TIMESTAMP
       );

INSERT INTO transactions (intention_id, seller_id, buyer_id, status, initiated_at)
VALUES (
           2,  -- intention_id (debe corresponder a un registro válido en operation_intents)
           1,  -- seller_id (debe corresponder a un registro válido en accounts)
           2,  -- buyer_id (debe corresponder a un registro válido en accounts)
           2,  -- status (suponiendo que 3 es un valor válido para el estado)
           CURRENT_TIMESTAMP
       );
