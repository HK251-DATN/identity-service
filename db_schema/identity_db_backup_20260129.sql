--
-- PostgreSQL database dump
--

\restrict xfLi3ob0bpax1aZbtVT9444HIUH6vMf7njfXjLc25DkIUBULBFNwUFnu7VY4lHL

-- Dumped from database version 16.11 (Debian 16.11-1.pgdg13+1)
-- Dumped by pg_dump version 16.11 (Ubuntu 16.11-0ubuntu0.24.04.1)

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: group_permission; Type: TABLE; Schema: public; Owner: khoidev
--

CREATE TABLE public.group_permission (
    grp_per_id bigint NOT NULL,
    granted_at timestamp without time zone,
    valid_until timestamp without time zone,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    is_active boolean,
    group_id bigint NOT NULL,
    per_id bigint NOT NULL
);


ALTER TABLE public.group_permission OWNER TO khoidev;

--
-- Name: group_permission_grp_per_id_seq; Type: SEQUENCE; Schema: public; Owner: khoidev
--

ALTER TABLE public.group_permission ALTER COLUMN grp_per_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.group_permission_grp_per_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: groups; Type: TABLE; Schema: public; Owner: khoidev
--

CREATE TABLE public.groups (
    group_id bigint NOT NULL,
    group_name character(255) NOT NULL,
    group_des character(255),
    is_active boolean,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);


ALTER TABLE public.groups OWNER TO khoidev;

--
-- Name: groups_group_id_seq; Type: SEQUENCE; Schema: public; Owner: khoidev
--

ALTER TABLE public.groups ALTER COLUMN group_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.groups_group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: permissions; Type: TABLE; Schema: public; Owner: khoidev
--

CREATE TABLE public.permissions (
    per_id bigint NOT NULL,
    per_code bigint NOT NULL,
    per_name character(255) NOT NULL,
    per_des character(255),
    is_active boolean,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);


ALTER TABLE public.permissions OWNER TO khoidev;

--
-- Name: permissions_per_id_seq; Type: SEQUENCE; Schema: public; Owner: khoidev
--

ALTER TABLE public.permissions ALTER COLUMN per_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.permissions_per_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: user_group; Type: TABLE; Schema: public; Owner: khoidev
--

CREATE TABLE public.user_group (
    user_group_id bigint NOT NULL,
    added_at timestamp without time zone,
    is_active timestamp without time zone,
    user_id bigint NOT NULL,
    group_id bigint NOT NULL
);


ALTER TABLE public.user_group OWNER TO khoidev;

--
-- Name: user_group_user_group_id_seq; Type: SEQUENCE; Schema: public; Owner: khoidev
--

ALTER TABLE public.user_group ALTER COLUMN user_group_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_group_user_group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: users; Type: TABLE; Schema: public; Owner: khoidev
--

CREATE TABLE public.users (
    user_id bigint NOT NULL,
    user_email character varying(255) NOT NULL,
    hashed_pw character varying(255) NOT NULL,
    updated_at timestamp without time zone,
    created_at timestamp without time zone
);


ALTER TABLE public.users OWNER TO khoidev;

--
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: khoidev
--

ALTER TABLE public.users ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.users_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: group_permission; Type: TABLE DATA; Schema: public; Owner: khoidev
--

COPY public.group_permission (grp_per_id, granted_at, valid_until, created_at, updated_at, is_active, group_id, per_id) FROM stdin;
\.


--
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: khoidev
--

COPY public.groups (group_id, group_name, group_des, is_active, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: permissions; Type: TABLE DATA; Schema: public; Owner: khoidev
--

COPY public.permissions (per_id, per_code, per_name, per_des, is_active, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: user_group; Type: TABLE DATA; Schema: public; Owner: khoidev
--

COPY public.user_group (user_group_id, added_at, is_active, user_id, group_id) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: khoidev
--

COPY public.users (user_id, user_email, hashed_pw, updated_at, created_at) FROM stdin;
\.


--
-- Name: group_permission_grp_per_id_seq; Type: SEQUENCE SET; Schema: public; Owner: khoidev
--

SELECT pg_catalog.setval('public.group_permission_grp_per_id_seq', 1, false);


--
-- Name: groups_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: khoidev
--

SELECT pg_catalog.setval('public.groups_group_id_seq', 1, false);


--
-- Name: permissions_per_id_seq; Type: SEQUENCE SET; Schema: public; Owner: khoidev
--

SELECT pg_catalog.setval('public.permissions_per_id_seq', 1, false);


--
-- Name: user_group_user_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: khoidev
--

SELECT pg_catalog.setval('public.user_group_user_group_id_seq', 1, false);


--
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: khoidev
--

SELECT pg_catalog.setval('public.users_user_id_seq', 3, true);


--
-- Name: group_permission group_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: khoidev
--

ALTER TABLE ONLY public.group_permission
    ADD CONSTRAINT group_permission_pkey PRIMARY KEY (grp_per_id);


--
-- Name: groups groups_group_name_key; Type: CONSTRAINT; Schema: public; Owner: khoidev
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_group_name_key UNIQUE (group_name);


--
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: public; Owner: khoidev
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (group_id);


--
-- Name: permissions permissions_per_code_key; Type: CONSTRAINT; Schema: public; Owner: khoidev
--

ALTER TABLE ONLY public.permissions
    ADD CONSTRAINT permissions_per_code_key UNIQUE (per_code);


--
-- Name: permissions permissions_per_name_key; Type: CONSTRAINT; Schema: public; Owner: khoidev
--

ALTER TABLE ONLY public.permissions
    ADD CONSTRAINT permissions_per_name_key UNIQUE (per_name);


--
-- Name: permissions permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: khoidev
--

ALTER TABLE ONLY public.permissions
    ADD CONSTRAINT permissions_pkey PRIMARY KEY (per_id);


--
-- Name: user_group user_group_pkey; Type: CONSTRAINT; Schema: public; Owner: khoidev
--

ALTER TABLE ONLY public.user_group
    ADD CONSTRAINT user_group_pkey PRIMARY KEY (user_group_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: khoidev
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- Name: users users_user_email_key; Type: CONSTRAINT; Schema: public; Owner: khoidev
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_user_email_key UNIQUE (user_email);


--
-- Name: group_permission group_permission_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: khoidev
--

ALTER TABLE ONLY public.group_permission
    ADD CONSTRAINT group_permission_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.groups(group_id) ON DELETE CASCADE NOT VALID;


--
-- Name: group_permission group_permission_per_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: khoidev
--

ALTER TABLE ONLY public.group_permission
    ADD CONSTRAINT group_permission_per_id_fkey FOREIGN KEY (per_id) REFERENCES public.permissions(per_id) ON DELETE CASCADE NOT VALID;


--
-- Name: user_group user_group_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: khoidev
--

ALTER TABLE ONLY public.user_group
    ADD CONSTRAINT user_group_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.groups(group_id) ON DELETE CASCADE NOT VALID;


--
-- Name: user_group user_group_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: khoidev
--

ALTER TABLE ONLY public.user_group
    ADD CONSTRAINT user_group_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE CASCADE NOT VALID;


--
-- PostgreSQL database dump complete
--

\unrestrict xfLi3ob0bpax1aZbtVT9444HIUH6vMf7njfXjLc25DkIUBULBFNwUFnu7VY4lHL

