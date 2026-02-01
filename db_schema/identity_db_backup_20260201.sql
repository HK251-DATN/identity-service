--
-- PostgreSQL database dump
--

\restrict A3ZKHauOizYhwx1PzRS1aLpsy375z2RN4CyjSgBit6gTZlY3bIPj5U6Dg4J5tyt

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
    valid_until timestamp without time zone,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
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
    group_name character varying(255) NOT NULL,
    group_des character varying(255),
    is_active boolean,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
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
    per_code character varying(255) NOT NULL,
    per_name character varying(255) NOT NULL,
    per_des character varying(255),
    is_active boolean,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
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
    added_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    user_id bigint NOT NULL,
    group_id bigint NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    updated_at timestamp without time zone
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
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
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

COPY public.group_permission (grp_per_id, valid_until, created_at, updated_at, is_active, group_id, per_id) FROM stdin;
11	2027-02-01 00:00:00	2026-02-01 12:51:47.532955	\N	t	2	11
12	2027-02-01 00:00:00	2026-02-01 12:51:47.532955	\N	t	2	12
13	2027-02-01 00:00:00	2026-02-01 12:51:47.532955	\N	t	2	13
14	2027-02-01 00:00:00	2026-02-01 12:51:47.532955	\N	t	2	14
15	2027-02-01 00:00:00	2026-02-01 12:51:47.532955	\N	t	2	15
36	2027-02-01 00:00:00	2026-02-01 14:07:24.673271	\N	t	2	11
37	2027-02-01 00:00:00	2026-02-01 14:07:24.673271	\N	t	2	12
38	2027-02-01 00:00:00	2026-02-01 14:07:24.673271	\N	t	2	13
39	2027-02-01 00:00:00	2026-02-01 14:07:24.673271	\N	t	2	14
40	2027-02-01 00:00:00	2026-02-01 14:07:24.673271	\N	t	2	15
41	2027-02-01 00:00:00	2026-02-01 14:11:07.033483	\N	t	1	1
42	2027-02-01 00:00:00	2026-02-01 14:11:07.033483	\N	t	1	2
43	2027-02-01 00:00:00	2026-02-01 14:11:07.033483	\N	t	1	3
44	2027-02-01 00:00:00	2026-02-01 14:11:07.033483	\N	t	1	4
45	2027-02-01 00:00:00	2026-02-01 14:11:07.033483	\N	t	1	5
46	2027-02-01 00:00:00	2026-02-01 14:11:07.033483	\N	t	1	6
47	2027-02-01 00:00:00	2026-02-01 14:11:07.033483	\N	t	1	7
48	2027-02-01 00:00:00	2026-02-01 14:11:07.033483	\N	t	1	8
49	2027-02-01 00:00:00	2026-02-01 14:11:07.033483	\N	t	1	9
50	2027-02-01 00:00:00	2026-02-01 14:11:07.033483	\N	t	1	10
\.


--
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: khoidev
--

COPY public.groups (group_id, group_name, group_des, is_active, created_at, updated_at) FROM stdin;
1	ADMIN	System administrators	t	\N	\N
2	MANAGER	Business managers	t	\N	\N
3	STAFF	Internal staff members	t	\N	\N
4	SELLER	Sellers on the platform	t	\N	\N
5	BUYER	End users / buyers	t	\N	\N
6	WAREHOUSE	Warehouse management staff	t	\N	\N
7	DELIVERY	Delivery and logistics staff	t	\N	\N
8	CONTENT	Content & marketing team	t	\N	\N
9	TECH	Technical & IT support	t	\N	\N
10	GUEST	Limited-access guest users	t	\N	\N
\.


--
-- Data for Name: permissions; Type: TABLE DATA; Schema: public; Owner: khoidev
--

COPY public.permissions (per_id, per_code, per_name, per_des, is_active, created_at, updated_at) FROM stdin;
1	USER_VIEW	View users	View user list and user details	t	2026-02-01 09:58:51.623872	\N
2	USER_CREATE	Create user	Create new user accounts	t	2026-02-01 09:58:51.623872	\N
3	USER_UPDATE	Update user	Update user information	t	2026-02-01 09:58:51.623872	\N
4	USER_DELETE	Delete user	Remove or deactivate users	t	2026-02-01 09:58:51.623872	\N
5	GROUP_VIEW	View groups	View user groups	t	2026-02-01 09:58:51.623872	\N
6	GROUP_MANAGE	Manage groups	Create, update, delete groups	t	2026-02-01 09:58:51.623872	\N
7	PERMISSION_VIEW	View permissions	View permission list	t	2026-02-01 09:58:51.623872	\N
8	PERMISSION_ASSIGN	Assign permissions	Assign permissions to groups	t	2026-02-01 09:58:51.623872	\N
9	ORDER_VIEW	View orders	View order information	t	2026-02-01 09:58:51.623872	\N
10	ORDER_MANAGE	Manage orders	Create, update, cancel orders	t	2026-02-01 09:58:51.623872	\N
11	PRODUCT_VIEW	View products	View product catalog	t	2026-02-01 09:58:51.623872	\N
12	PRODUCT_MANAGE	Manage products	Create, update, delete products	t	2026-02-01 09:58:51.623872	\N
13	CONTENT_MANAGE	Manage content	Manage posts, banners, blogs	t	2026-02-01 09:58:51.623872	\N
14	WAREHOUSE_MANAGE	Manage warehouse	Manage inventory and stock	t	2026-02-01 09:58:51.623872	\N
15	DELIVERY_MANAGE	Manage delivery	Assign and track deliveries	t	2026-02-01 09:58:51.623872	\N
16	SYSTEM_CONFIG	System config	Configure system settings	t	2026-02-01 09:58:51.623872	\N
\.


--
-- Data for Name: user_group; Type: TABLE DATA; Schema: public; Owner: khoidev
--

COPY public.user_group (user_group_id, added_at, user_id, group_id, is_active, updated_at) FROM stdin;
1	\N	4	1	t	\N
2	\N	5	1	t	\N
3	\N	6	1	t	\N
4	\N	7	1	t	\N
5	\N	8	1	t	\N
7	\N	4	2	t	\N
8	\N	5	2	t	\N
9	\N	6	2	t	\N
10	\N	7	2	t	\N
11	\N	8	2	t	\N
12	\N	9	2	t	\N
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: khoidev
--

COPY public.users (user_id, user_email, hashed_pw, updated_at, created_at) FROM stdin;
4	user@gmail.com	$2a$10$P7kntbPbhDATxy39vo43HuBWD6O5TvdEgPq06CrSsUoLO1Ma9DPwK	\N	2026-01-30 07:13:35.490082
5	user1@gmail.com	$2a$10$J13tMdt3TcwNvByd8RUR.eHpfKPYFPnT17RdjeiVCMCozLvADs6nS	\N	2026-02-01 15:12:28.497499
6	user2@gmail.com	$2a$10$z0VNFzCkGcRUvyi9CefUuOzthl0NS.XpEKMWQy.4NBjqrswTxQmtW	\N	2026-02-01 15:12:31.982906
7	user3@gmail.com	$2a$10$Mzel5whtYrGPcO3fdyLx7.w5FiLDqel8DrYCRcquAfPDiT0kAhdUW	\N	2026-02-01 15:12:33.450146
8	user4@gmail.com	$2a$10$UdtiEfd/EKVThjJFFcPrLu4fJjzbOVR0cmybWUMFhKVXoAzhWLy7W	\N	2026-02-01 15:12:34.667571
9	user5@gmail.com	$2a$10$CiP7zIapDxbWHed0ZFd1v.Asu.DHdR9O4JpRMSQrx4W9V8eQFvTZ6	\N	2026-02-01 15:12:35.888538
10	user6@gmail.com	$2a$10$fbsajSSVBaqfJTOj53OHmun0.mk9J.7x3n.u/OkUVrnIsY2MNarOS	\N	2026-02-01 15:12:37.336431
11	user7@gmail.com	$2a$10$fU8aJMDteE0qzwHB2SroXuQqT4n4nfONQeJ7LDB76vGUHBJ1VIur2	\N	2026-02-01 15:12:39.480072
12	user8@gmail.com	$2a$10$Nsu7kT1CKOGByJ9atkcv1.dIKahJ.m/4KoLhyJNcPB4UEpuxrDHQG	\N	2026-02-01 15:12:42.763908
\.


--
-- Name: group_permission_grp_per_id_seq; Type: SEQUENCE SET; Schema: public; Owner: khoidev
--

SELECT pg_catalog.setval('public.group_permission_grp_per_id_seq', 51, true);


--
-- Name: groups_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: khoidev
--

SELECT pg_catalog.setval('public.groups_group_id_seq', 10, true);


--
-- Name: permissions_per_id_seq; Type: SEQUENCE SET; Schema: public; Owner: khoidev
--

SELECT pg_catalog.setval('public.permissions_per_id_seq', 16, true);


--
-- Name: user_group_user_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: khoidev
--

SELECT pg_catalog.setval('public.user_group_user_group_id_seq', 15, true);


--
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: khoidev
--

SELECT pg_catalog.setval('public.users_user_id_seq', 12, true);


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
-- Name: user_group user_group_user_id_group_id_key; Type: CONSTRAINT; Schema: public; Owner: khoidev
--

ALTER TABLE ONLY public.user_group
    ADD CONSTRAINT user_group_user_id_group_id_key UNIQUE (user_id, group_id);


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

\unrestrict A3ZKHauOizYhwx1PzRS1aLpsy375z2RN4CyjSgBit6gTZlY3bIPj5U6Dg4J5tyt

