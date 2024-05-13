INSERT INTO roles (name) VALUES ('Administrator Systemu');
INSERT INTO roles (name) VALUES ('Konstruktor Maszyn');
INSERT INTO roles (name) VALUES ('Nadzorca Magazynu');
INSERT INTO roles (name) VALUES ('Sprzedawca');
INSERT INTO roles (name) VALUES ('Pracownik Produkcji');

INSERT INTO users (name, email, password, role_id) VALUES ('Admin', 'admin@blastmachine.com', 'password', (SELECT id FROM roles WHERE name = 'Administrator Systemu'));
INSERT INTO users (name, email, password, role_id) VALUES ('Kamil Matusz', 'kamilmatusz@test.com', 'password', (SELECT id FROM roles WHERE name = 'Sprzedawca'));
INSERT INTO users (name, email, password, role_id) VALUES ('Konrad Ryż', 'konradryz@test.com', 'password', (SELECT id FROM roles WHERE name = 'Konstruktor Maszyn'));
INSERT INTO users (name, email, password, role_id) VALUES ('Jan Papryka', 'janpapryka@test.com', 'password', (SELECT id FROM roles WHERE name = 'Nadzorca Magazynu'));

INSERT INTO clients (name, email, phone_number, address) VALUES ('Jan Kowalski', 'jankowalski@example.com', '+48123456789', 'Konopnickiej 1, Rzeszów, Polska');
INSERT INTO clients (name, email, phone_number, address) VALUES ('Paweł Kowal', 'pawelkowal@example.com', '+48987654321', 'Cieplownicza 24, Rzeszów, Polska');

INSERT INTO materials (name, price, amount) VALUES ('Blacha Perforowana', 10.50, 100);
INSERT INTO materials (name, price, amount) VALUES ('Łopata wirnika', 30.00, 50);
INSERT INTO materials (name, price, amount) VALUES ('Transformator', 2000.00, 10);
INSERT INTO materials (name, price, amount) VALUES ('Proszek malowniczy', 50.00, 10);
INSERT INTO materials (name, price, amount) VALUES ('Śruby z teflonem', 0.20, 5000);
INSERT INTO materials (name, price, amount) VALUES ('Śruby bez teflonu', 0.10, 5000);
INSERT INTO materials (name, price, amount) VALUES ('Wentylator', 300.00, 100);
INSERT INTO materials (name, price, amount) VALUES ('Gniazdo Zasilające', 20.00, 400);
INSERT INTO materials (name, price, amount) VALUES ('Naklejka', 0.05, 4000);
INSERT INTO materials (name, price, amount) VALUES ('Panel HMI', 400.00, 100);
INSERT INTO materials (name, price, amount) VALUES ('Stycznik', 29.00, 700);

INSERT INTO order_states (name) VALUES ('Przyjęte');
INSERT INTO order_states (name) VALUES ('Skompletowane');
INSERT INTO order_states (name) VALUES ('Wysłane');
INSERT INTO order_states (name) VALUES ('Odebrane');

INSERT INTO accesories (name, price) VALUES ('Zawieszka', 50.00);

INSERT INTO models (name, price, comments) VALUES ('Standard', 50000.00, 'Podstawowy model śrutownicy');
INSERT INTO models (name, price, comments) VALUES ('XL', 10000.00, 'Model w wersji XL z powiększonym koszem');
INSERT INTO models (name, price, comments) VALUES ('XXL', 10000.00, 'Model w wersji XXL z maksymalnym koszem');

INSERT INTO machines (model_id) VALUES ((SELECT id FROM models WHERE name = 'Standard'));
INSERT INTO machines (model_id) VALUES ((SELECT id FROM models WHERE name = 'XL'));
INSERT INTO machines (model_id) VALUES ((SELECT id FROM models WHERE name = 'XXL'));

INSERT INTO orders (price, date, comments, user_id, client_id, state_id, machine_id)
VALUES (30000.00, NOW(), 'Zamówienie zakończone', 4,
        (SELECT id FROM clients WHERE name = 'Jan Kowalski'), (SELECT id FROM order_states WHERE name = 'Skompletowane'),
        (SELECT id FROM machines WHERE model_id = (SELECT id FROM models WHERE name = 'Standard')));
