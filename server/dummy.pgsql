--
-- PostgreSQL database dump
--

-- Dumped from database version 10.2
-- Dumped by pg_dump version 10.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: bakeryuser; Type: TABLE; Schema: public; Owner: sergeypanov
--

CREATE TABLE bakeryuser (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    phone_number character varying(255) NOT NULL,
    surname character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);


ALTER TABLE bakeryuser OWNER TO sergeypanov;

--
-- Name: car; Type: TABLE; Schema: public; Owner: sergeypanov
--

CREATE TABLE car (
    id bigint NOT NULL,
    date_of_acquire timestamp without time zone,
    type character varying(255)
);


ALTER TABLE car OWNER TO sergeypanov;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: sergeypanov
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hibernate_sequence OWNER TO sergeypanov;

--
-- Name: ingredient; Type: TABLE; Schema: public; Owner: sergeypanov
--

CREATE TABLE ingredient (
    id bigint NOT NULL,
    besf_before timestamp without time zone,
    date_of_manufacture timestamp without time zone,
    name character varying(255) NOT NULL,
    stored integer NOT NULL,
    supplier character varying(255) NOT NULL
);


ALTER TABLE ingredient OWNER TO sergeypanov;

--
-- Name: item; Type: TABLE; Schema: public; Owner: sergeypanov
--

CREATE TABLE item (
    id bigint NOT NULL,
    ordered_amount integer,
    order_id bigint NOT NULL,
    product bigint NOT NULL,
    CONSTRAINT item_ordered_amount_check CHECK (((ordered_amount >= 1) AND (ordered_amount <= 10)))
);


ALTER TABLE item OWNER TO sergeypanov;

--
-- Name: order; Type: TABLE; Schema: public; Owner: sergeypanov
--

CREATE TABLE "order" (
    id bigint NOT NULL,
    createdate timestamp without time zone NOT NULL,
    exportdate timestamp without time zone NOT NULL,
    state character varying(255) NOT NULL,
    car_id bigint,
    user_id bigint NOT NULL
);


ALTER TABLE "order" OWNER TO sergeypanov;

--
-- Name: product; Type: TABLE; Schema: public; Owner: sergeypanov
--

CREATE TABLE product (
    id bigint NOT NULL,
    energy_value character varying(255),
    name character varying(255) NOT NULL,
    total_amount integer
);


ALTER TABLE product OWNER TO sergeypanov;

--
-- Name: product_ingredient; Type: TABLE; Schema: public; Owner: sergeypanov
--

CREATE TABLE product_ingredient (
    product_id bigint NOT NULL,
    ingredient_id bigint NOT NULL
);


ALTER TABLE product_ingredient OWNER TO sergeypanov;

--
-- Name: role; Type: TABLE; Schema: public; Owner: sergeypanov
--

CREATE TABLE role (
    name character varying(255) NOT NULL
);


ALTER TABLE role OWNER TO sergeypanov;

--
-- Name: user; Type: TABLE; Schema: public; Owner: sergeypanov
--

CREATE TABLE "user" (
    id bigint NOT NULL,
    city character varying(255),
    psc character varying(255),
    street_name character varying(255),
    street_number integer,
    email character varying(255) NOT NULL,
    firstname character varying(255) NOT NULL,
    lastname character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    phone_number character varying(255),
    username character varying(255) NOT NULL
);


ALTER TABLE "user" OWNER TO sergeypanov;

--
-- Name: user_role; Type: TABLE; Schema: public; Owner: sergeypanov
--

CREATE TABLE user_role (
    user_id bigint NOT NULL,
    role_id character varying(255) NOT NULL
);


ALTER TABLE user_role OWNER TO sergeypanov;

--
-- Name: usersorder; Type: TABLE; Schema: public; Owner: sergeypanov
--

CREATE TABLE usersorder (
    id bigint NOT NULL,
    is_delivery boolean,
    orderdate timestamp without time zone,
    state character varying(255),
    bakeryuser bigint NOT NULL,
    car bigint
);


ALTER TABLE usersorder OWNER TO sergeypanov;

--
-- Data for Name: bakeryuser; Type: TABLE DATA; Schema: public; Owner: sergeypanov
--

COPY bakeryuser (id, email, name, password, phone_number, surname, username) FROM stdin;
10	panovsy@gmail.com	Sergey	$2a$10$6fVwHSrZT2WxyVh054IhuOPyhTomhS.IkBOsD0gpP7qWi2wG9oLtC	+420 66 66 66	Panov	user
\.


--
-- Data for Name: car; Type: TABLE DATA; Schema: public; Owner: sergeypanov
--

COPY car (id, date_of_acquire, type) FROM stdin;
\.


--
-- Data for Name: ingredient; Type: TABLE DATA; Schema: public; Owner: sergeypanov
--

COPY ingredient (id, besf_before, date_of_manufacture, name, stored, supplier) FROM stdin;
\.


--
-- Data for Name: item; Type: TABLE DATA; Schema: public; Owner: sergeypanov
--

COPY item (id, ordered_amount, order_id, product) FROM stdin;
\.


--
-- Data for Name: order; Type: TABLE DATA; Schema: public; Owner: sergeypanov
--

COPY "order" (id, createdate, exportdate, state, car_id, user_id) FROM stdin;
34	2018-02-02 01:00:00	2018-02-02 01:00:00	ACCEPTED	\N	10
\.


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: sergeypanov
--

COPY product (id, energy_value, name, total_amount) FROM stdin;
\.


--
-- Data for Name: product_ingredient; Type: TABLE DATA; Schema: public; Owner: sergeypanov
--

COPY product_ingredient (product_id, ingredient_id) FROM stdin;
\.


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: sergeypanov
--

COPY role (name) FROM stdin;
USER
ADMIN
EMPLOYEE
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: sergeypanov
--

COPY "user" (id, city, psc, street_name, street_number, email, firstname, lastname, password, phone_number, username) FROM stdin;
10	\N	\N	\N	\N	pity@gmail.com	Renault	Megan	$2a$10$KCISgZDHJ6ZJ/5Zbw2zGZerjyMYr8dx2NOeswq12WkuXknkaAI2/i	\N	Kocka
11	\N	\N	\N	\N	pity@gmail.com	Renault	Megan	$2a$10$BUsHcc1C0MGY4htnrKRvY.QYmZl.FRHxiAermHl6SmgBfYiarCUIm	\N	admin
\.


--
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: sergeypanov
--

COPY user_role (user_id, role_id) FROM stdin;
10	USER
\.


--
-- Data for Name: usersorder; Type: TABLE DATA; Schema: public; Owner: sergeypanov
--

COPY usersorder (id, is_delivery, orderdate, state, bakeryuser, car) FROM stdin;
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: sergeypanov
--

SELECT pg_catalog.setval('hibernate_sequence', 1, true);


--
-- Name: bakeryuser bakeryuser_pkey; Type: CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY bakeryuser
    ADD CONSTRAINT bakeryuser_pkey PRIMARY KEY (id);


--
-- Name: car car_pkey; Type: CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY car
    ADD CONSTRAINT car_pkey PRIMARY KEY (id);


--
-- Name: ingredient ingredient_pkey; Type: CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY ingredient
    ADD CONSTRAINT ingredient_pkey PRIMARY KEY (id);


--
-- Name: item item_pkey; Type: CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY item
    ADD CONSTRAINT item_pkey PRIMARY KEY (id);


--
-- Name: order order_pkey; Type: CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT order_pkey PRIMARY KEY (id);


--
-- Name: product_ingredient product_ingredient_pkey; Type: CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY product_ingredient
    ADD CONSTRAINT product_ingredient_pkey PRIMARY KEY (product_id, ingredient_id);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (name);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- Name: usersorder usersorder_pkey; Type: CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY usersorder
    ADD CONSTRAINT usersorder_pkey PRIMARY KEY (id);


--
-- Name: item fk19476af61djyu689w2l0ktyxf; Type: FK CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY item
    ADD CONSTRAINT fk19476af61djyu689w2l0ktyxf FOREIGN KEY (order_id) REFERENCES usersorder(id);


--
-- Name: product_ingredient fk82j6ju1bhetgb0q2snlosewwb; Type: FK CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY product_ingredient
    ADD CONSTRAINT fk82j6ju1bhetgb0q2snlosewwb FOREIGN KEY (product_id) REFERENCES product(id);


--
-- Name: user_role fka68196081fvovjhkek5m97n3y; Type: FK CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT fka68196081fvovjhkek5m97n3y FOREIGN KEY (role_id) REFERENCES role(name);


--
-- Name: user_role fkdn0lt5xrkbcxfwaa2jxj0ifg6; Type: FK CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT fkdn0lt5xrkbcxfwaa2jxj0ifg6 FOREIGN KEY (user_id) REFERENCES bakeryuser(id);


--
-- Name: usersorder fki3tkle199nsoq3k09glega0sm; Type: FK CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY usersorder
    ADD CONSTRAINT fki3tkle199nsoq3k09glega0sm FOREIGN KEY (car) REFERENCES car(id);


--
-- Name: usersorder fkifw4293de1544u353dugkav3u; Type: FK CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY usersorder
    ADD CONSTRAINT fkifw4293de1544u353dugkav3u FOREIGN KEY (bakeryuser) REFERENCES bakeryuser(id);


--
-- Name: item fkj6bsjbdyh79ok2w0rvbgew5ao; Type: FK CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY item
    ADD CONSTRAINT fkj6bsjbdyh79ok2w0rvbgew5ao FOREIGN KEY (product) REFERENCES product(id);


--
-- Name: product_ingredient fkoexfkyxqal5o2c6cnendmu58e; Type: FK CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY product_ingredient
    ADD CONSTRAINT fkoexfkyxqal5o2c6cnendmu58e FOREIGN KEY (ingredient_id) REFERENCES ingredient(id);


--
-- Name: order fkrcaf946w0bh6qj0ljiw3pwpnu; Type: FK CONSTRAINT; Schema: public; Owner: sergeypanov
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT fkrcaf946w0bh6qj0ljiw3pwpnu FOREIGN KEY (user_id) REFERENCES "user"(id);


--
-- PostgreSQL database dump complete
--

