-- auto-generated definition
CREATE TABLE car
(
  id       BIGINT       NOT NULL
    CONSTRAINT car_pkey
    PRIMARY KEY,
  date_add TIMESTAMP    NOT NULL,
  type     VARCHAR(255) NOT NULL
);

-- auto-generated definition
CREATE TABLE role
(
  id   BIGINT       NOT NULL
    CONSTRAINT role_pkey
    PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);


-- auto-generated definition
CREATE TABLE "user"
(
  id            BIGINT       NOT NULL
    CONSTRAINT user_pkey
    PRIMARY KEY,
  city          VARCHAR(255),
  psc           VARCHAR(255),
  street_name   VARCHAR(255),
  street_number INTEGER,
  email         VARCHAR(255) NOT NULL,
  firstname     VARCHAR(255) NOT NULL,
  lastname      VARCHAR(255) NOT NULL,
  password      VARCHAR(255) NOT NULL,
  phone_number  VARCHAR(255),
  username      VARCHAR(255) NOT NULL,
  role_id       BIGINT
    CONSTRAINT fkdl9dqp078pc03g6kdnxmnlqpc
    REFERENCES role
);


-- auto-generated definition
CREATE TABLE "order"
(
  id            BIGINT    NOT NULL
    CONSTRAINT order_pkey
    PRIMARY KEY,
  city          VARCHAR(255),
  psc           VARCHAR(255),
  street_name   VARCHAR(255),
  street_number INTEGER,
  createdate    TIMESTAMP,
  exportdate    TIMESTAMP NOT NULL,
  state         VARCHAR(255),
  car_id        BIGINT
    CONSTRAINT fkshbpqic70fhwkvd65vktohxr9
    REFERENCES car,
  user_id       BIGINT    NOT NULL
    CONSTRAINT fkrcaf946w0bh6qj0ljiw3pwpnu
    REFERENCES "user"
);

-- auto-generated definition
CREATE TABLE ingredient
(
  id       BIGINT       NOT NULL
    CONSTRAINT ingredient_pkey
    PRIMARY KEY,
  name     VARCHAR(255) NOT NULL,
  supplier VARCHAR(255) NOT NULL
);

-- auto-generated definition
CREATE TABLE product
(
  id           BIGINT       NOT NULL
    CONSTRAINT product_pkey
    PRIMARY KEY,
  image        BYTEA,
  name         VARCHAR(255) NOT NULL,
  price        REAL,
  total_amount INTEGER
);

-- auto-generated definition
CREATE TABLE item
(
  id            BIGINT  NOT NULL
    CONSTRAINT item_pkey
    PRIMARY KEY,
  count_ordered INTEGER NOT NULL
    CONSTRAINT item_count_ordered_check
    CHECK (count_ordered >= 1),
  order_id      BIGINT  NOT NULL
    CONSTRAINT fksoe5dvc8cerpp4613l3c7ngmp
    REFERENCES "order",
  product_id    BIGINT  NOT NULL
    CONSTRAINT fkd1g72rrhgq1sf7m4uwfvuhlhe
    REFERENCES product
);






-- auto-generated definition
CREATE TABLE product_ingredient
(
  product_id    BIGINT NOT NULL
    CONSTRAINT fk82j6ju1bhetgb0q2snlosewwb
    REFERENCES product,
  ingredient_id BIGINT NOT NULL
    CONSTRAINT fkoexfkyxqal5o2c6cnendmu58e
    REFERENCES ingredient,
  CONSTRAINT product_ingredient_pkey
  PRIMARY KEY (product_id, ingredient_id)
);







INSERT INTO role (id,name) VALUES (1,'USER');
INSERT INTO role (id,name) VALUES (2,'ADMIN');
INSERT INTO role (id,name) VALUES (3,'EMPLOYEE');

INSERT INTO "user" (id, city, psc, street_name,
                    street_number, email, firstname,
                    lastname, password, phone_number,
                    username, role_id) VALUES (
    1, '', '', '', -1, 'admin@admin.xyz', 'AdminFName', 'AdminLName', '$2a$10$mhiq/C2l7suUM7U2n48lfu3u7LzHYk1/DK5WioB0W7WWeF6XfTWji', '', 'admin', 2
);