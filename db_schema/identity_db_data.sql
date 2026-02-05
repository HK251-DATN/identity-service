--
-- PostgreSQL database dump
--

\restrict 5HIcG5LnX39rxfqViLIRd79eJiwKaqahERQXMkP4o9vMfxhNrENaFZMd1GbvyGl

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
-- Name: group_permission; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.group_permission (
    grp_per_id bigint NOT NULL,
    valid_until timestamp without time zone,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone,
    is_active boolean,
    group_id bigint NOT NULL,
    per_id bigint NOT NULL
);


--
-- Name: group_permission_grp_per_id_seq; Type: SEQUENCE; Schema: public; Owner: -
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
-- Name: groups; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.groups (
    group_id bigint NOT NULL,
    group_name character varying(255) NOT NULL,
    group_des character varying(255),
    is_active boolean,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone
);


--
-- Name: groups_group_id_seq; Type: SEQUENCE; Schema: public; Owner: -
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
-- Name: permissions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.permissions (
    per_id bigint NOT NULL,
    per_code character varying(255) NOT NULL,
    per_name character varying(255) NOT NULL,
    per_des character varying(255),
    is_active boolean,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone
);


--
-- Name: permissions_per_id_seq; Type: SEQUENCE; Schema: public; Owner: -
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
-- Name: user_group; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.user_group (
    user_group_id bigint NOT NULL,
    added_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    user_id bigint NOT NULL,
    group_id bigint NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    updated_at timestamp without time zone
);


--
-- Name: user_group_user_group_id_seq; Type: SEQUENCE; Schema: public; Owner: -
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
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    user_id bigint NOT NULL,
    user_email character varying(255) NOT NULL,
    hashed_pw character varying(255) NOT NULL,
    updated_at timestamp without time zone,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


--
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: -
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
-- Data for Name: group_permission; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.group_permission (grp_per_id, valid_until, created_at, updated_at, is_active, group_id, per_id) FROM stdin;
1	2027-01-01 00:00:00	2026-02-04 17:28:32.003023	\N	t	1	1
2	2027-01-01 00:00:00	2026-02-04 17:28:32.003023	\N	t	1	2
3	2027-01-01 00:00:00	2026-02-04 17:28:32.003023	\N	t	1	3
4	2027-01-01 00:00:00	2026-02-04 17:28:32.003023	\N	t	1	4
5	2027-01-01 00:00:00	2026-02-04 17:28:32.003023	\N	t	1	5
6	2027-01-01 00:00:00	2026-02-04 17:28:32.003023	\N	t	1	6
7	2027-01-01 00:00:00	2026-02-04 17:28:32.003023	\N	t	1	7
8	2027-01-01 00:00:00	2026-02-04 17:28:32.003023	\N	t	1	8
9	2027-02-02 00:00:00	2026-02-05 06:39:23.629237	\N	t	2	9
\.


--
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.groups (group_id, group_name, group_des, is_active, created_at, updated_at) FROM stdin;
1	ADMIN	System administrators	t	2026-02-04 17:17:36.84909	\N
2	BUYER	End users / buyers	t	2026-02-04 17:17:36.84909	\N
3	GUEST	Limited-access guest users	t	2026-02-04 17:17:36.84909	\N
\.


--
-- Data for Name: permissions; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.permissions (per_id, per_code, per_name, per_des, is_active, created_at, updated_at) FROM stdin;
1	USER_VIEW	View users	View user list and user details	t	2026-02-01 09:58:51.623872	\N
2	USER_CREATE	Create user	Create new user accounts	t	2026-02-01 09:58:51.623872	\N
3	USER_UPDATE	Update user	Update user information	t	2026-02-01 09:58:51.623872	\N
4	USER_DELETE	Delete user	Remove or deactivate users	t	2026-02-01 09:58:51.623872	\N
5	GROUP_VIEW	View groups	View user groups	t	2026-02-01 09:58:51.623872	\N
6	GROUP_MANAGE	Manage groups	Create, update, delete groups	t	2026-02-01 09:58:51.623872	\N
7	PERMISSION_VIEW	View permissions	View permission list	t	2026-02-01 09:58:51.623872	\N
8	PERMISSION_MANAGE	Manage permissions	CRUD permission and grant-revoke permission to groups	t	2026-02-01 09:58:51.623872	\N
9	CHAT_WITH_AI	Chat with AI Chatbox	Can access AI chatbox	t	2026-02-05 06:38:21.543238	\N
\.


--
-- Data for Name: user_group; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.user_group (user_group_id, added_at, user_id, group_id, is_active, updated_at) FROM stdin;
1	2026-02-04 17:26:36.15115	1	1	t	\N
2	2026-02-05 06:35:39.854226	2	2	t	\N
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.users (user_id, user_email, hashed_pw, updated_at, created_at) FROM stdin;
1	admin@gmail.com	$2a$10$TS/OQ90y76c7uc4z3va6N.5e6d0i.HWu6gescx8b9yGbo3Fnd5gdu	\N	2026-02-04 17:07:35.323448
2	buyer@gmail.com	$2a$10$5pKnKaBwOVm9dCgibuM3gew3rtE00.4RbZchHrpMxfHiSzdMrvr9O	\N	2026-02-05 06:32:11.486439
\.


--
-- Name: group_permission_grp_per_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.group_permission_grp_per_id_seq', 9, true);


--
-- Name: groups_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.groups_group_id_seq', 4, true);


--
-- Name: permissions_per_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.permissions_per_id_seq', 9, true);


--
-- Name: user_group_user_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.user_group_user_group_id_seq', 2, true);


--
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.users_user_id_seq', 2, true);


--
-- Name: group_permission group_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.group_permission
    ADD CONSTRAINT group_permission_pkey PRIMARY KEY (grp_per_id);


--
-- Name: groups groups_group_name_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_group_name_key UNIQUE (group_name);


--
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (group_id);


--
-- Name: permissions permissions_per_code_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.permissions
    ADD CONSTRAINT permissions_per_code_key UNIQUE (per_code);


--
-- Name: permissions permissions_per_name_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.permissions
    ADD CONSTRAINT permissions_per_name_key UNIQUE (per_name);


--
-- Name: permissions permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.permissions
    ADD CONSTRAINT permissions_pkey PRIMARY KEY (per_id);


--
-- Name: user_group user_group_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_group
    ADD CONSTRAINT user_group_pkey PRIMARY KEY (user_group_id);


--
-- Name: user_group user_group_user_id_group_id_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_group
    ADD CONSTRAINT user_group_user_id_group_id_key UNIQUE (user_id, group_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- Name: users users_user_email_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_user_email_key UNIQUE (user_email);


--
-- Name: group_permission group_permission_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.group_permission
    ADD CONSTRAINT group_permission_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.groups(group_id) ON DELETE CASCADE NOT VALID;


--
-- Name: group_permission group_permission_per_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.group_permission
    ADD CONSTRAINT group_permission_per_id_fkey FOREIGN KEY (per_id) REFERENCES public.permissions(per_id) ON DELETE CASCADE NOT VALID;


--
-- Name: user_group user_group_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_group
    ADD CONSTRAINT user_group_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.groups(group_id) ON DELETE CASCADE NOT VALID;


--
-- Name: user_group user_group_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_group
    ADD CONSTRAINT user_group_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE CASCADE NOT VALID;


--
-- PostgreSQL database dump complete
--

\unrestrict 5HIcG5LnX39rxfqViLIRd79eJiwKaqahERQXMkP4o9vMfxhNrENaFZMd1GbvyGl

