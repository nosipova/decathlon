CREATE TABLE stock (id_stock BIGSERIAL  PRIMARY KEY, total_quantity INT NOT NULL);

CREATE TABLE shoe (id_shoe BIGSERIAL  PRIMARY KEY, name VARCHAR(250) NOT NULL, size INT NOT NULL, color VARCHAR(250) NOT NULL, quantity INT NOT NULL, id_stock INT NOT NULL REFERENCES stock(id_stock));