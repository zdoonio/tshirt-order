--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.13
-- Dumped by pg_dump version 9.6.13

-- Started on 2020-05-10 23:15:15

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12387)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 187 (class 1259 OID 263137)
-- Name: order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 190 (class 1259 OID 263163)
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    id integer DEFAULT nextval('public.order_id_seq'::regclass) NOT NULL,
    create_date date NOT NULL
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 263141)
-- Name: stock_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.stock_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.stock_id_seq OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 263132)
-- Name: stock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stock (
    id integer DEFAULT nextval('public.stock_id_seq'::regclass) NOT NULL,
    item_number integer NOT NULL,
    t_shirt_id integer NOT NULL
);


ALTER TABLE public.stock OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 263143)
-- Name: t_shirt_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.t_shirt_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_shirt_id_seq OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 263173)
-- Name: t_shirt_order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.t_shirt_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_shirt_order_id_seq OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 263168)
-- Name: t_shirt_order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_shirt_order (
    id integer DEFAULT nextval('public.t_shirt_order_id_seq'::regclass) NOT NULL,
    name character varying(100) NOT NULL,
    age integer NOT NULL,
    t_shirt_id integer NOT NULL,
    order_id integer NOT NULL
);


ALTER TABLE public.t_shirt_order OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 263114)
-- Name: t_shirts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_shirts (
    id integer DEFAULT nextval('public.t_shirt_id_seq'::regclass) NOT NULL,
    color text NOT NULL,
    size text NOT NULL
);


ALTER TABLE public.t_shirts OWNER TO postgres;

--
-- TOC entry 2168 (class 0 OID 0)
-- Dependencies: 187
-- Name: order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_id_seq', 7, true);


--
-- TOC entry 2157 (class 0 OID 263163)
-- Dependencies: 190
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2153 (class 0 OID 263132)
-- Dependencies: 186
-- Data for Name: stock; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.stock (id, item_number, t_shirt_id) VALUES (1, 14, 1);
INSERT INTO public.stock (id, item_number, t_shirt_id) VALUES (2, 23, 2);
INSERT INTO public.stock (id, item_number, t_shirt_id) VALUES (3, 21, 3);
INSERT INTO public.stock (id, item_number, t_shirt_id) VALUES (4, 7, 4);
INSERT INTO public.stock (id, item_number, t_shirt_id) VALUES (5, 16, 5);
INSERT INTO public.stock (id, item_number, t_shirt_id) VALUES (6, 20, 6);
INSERT INTO public.stock (id, item_number, t_shirt_id) VALUES (7, 18, 7);
INSERT INTO public.stock (id, item_number, t_shirt_id) VALUES (8, 8, 8);
INSERT INTO public.stock (id, item_number, t_shirt_id) VALUES (9, 12, 9);
INSERT INTO public.stock (id, item_number, t_shirt_id) VALUES (10, 16, 10);
INSERT INTO public.stock (id, item_number, t_shirt_id) VALUES (11, 15, 11);
INSERT INTO public.stock (id, item_number, t_shirt_id) VALUES (12, 10, 12);


--
-- TOC entry 2169 (class 0 OID 0)
-- Dependencies: 188
-- Name: stock_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.stock_id_seq', 12, true);


--
-- TOC entry 2170 (class 0 OID 0)
-- Dependencies: 189
-- Name: t_shirt_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.t_shirt_id_seq', 12, true);


--
-- TOC entry 2158 (class 0 OID 263168)
-- Dependencies: 191
-- Data for Name: t_shirt_order; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2171 (class 0 OID 0)
-- Dependencies: 192
-- Name: t_shirt_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.t_shirt_order_id_seq', 6, true);


--
-- TOC entry 2152 (class 0 OID 263114)
-- Dependencies: 185
-- Data for Name: t_shirts; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.t_shirts (id, color, size) VALUES (5, 'niebieski', 'S');
INSERT INTO public.t_shirts (id, color, size) VALUES (6, 'niebieski', 'M');
INSERT INTO public.t_shirts (id, color, size) VALUES (7, 'niebieski', 'L');
INSERT INTO public.t_shirts (id, color, size) VALUES (8, 'niebieski', 'XL');
INSERT INTO public.t_shirts (id, color, size) VALUES (9, 'zielony', 'M');
INSERT INTO public.t_shirts (id, color, size) VALUES (10, 'zielony', 'S');
INSERT INTO public.t_shirts (id, color, size) VALUES (11, 'zielony', 'L');
INSERT INTO public.t_shirts (id, color, size) VALUES (12, 'zielony', 'XL');
INSERT INTO public.t_shirts (id, color, size) VALUES (1, 'czerwony', 'S');
INSERT INTO public.t_shirts (id, color, size) VALUES (2, 'czerwony', 'M');
INSERT INTO public.t_shirts (id, color, size) VALUES (3, 'czerwony', 'L');
INSERT INTO public.t_shirts (id, color, size) VALUES (4, 'czerwony', 'XL');


--
-- TOC entry 2029 (class 2606 OID 263167)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- TOC entry 2027 (class 2606 OID 263136)
-- Name: stock stock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_pkey PRIMARY KEY (id);


--
-- TOC entry 2025 (class 2606 OID 263121)
-- Name: t_shirts t_shirt_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_shirts
    ADD CONSTRAINT t_shirt_pkey PRIMARY KEY (id);


--
-- TOC entry 2031 (class 2606 OID 263172)
-- Name: t_shirt_order tshirt_order_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_shirt_order
    ADD CONSTRAINT tshirt_order_pkey PRIMARY KEY (id);


--
-- TOC entry 2034 (class 2606 OID 263187)
-- Name: t_shirt_order order_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_shirt_order
    ADD CONSTRAINT order_fk FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- TOC entry 2032 (class 2606 OID 263158)
-- Name: stock t_shirt_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT t_shirt_fk FOREIGN KEY (t_shirt_id) REFERENCES public.t_shirts(id);


--
-- TOC entry 2033 (class 2606 OID 263182)
-- Name: t_shirt_order t_shirt_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_shirt_order
    ADD CONSTRAINT t_shirt_fk FOREIGN KEY (t_shirt_id) REFERENCES public.t_shirts(id);


-- Completed on 2020-05-10 23:15:16

--
-- PostgreSQL database dump complete
--

