CREATE TABLE IF NOT EXISTS public.category
(
    id bigint NOT NULL,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT category_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE category_id_seq START 1;

CREATE TABLE IF NOT EXISTS public.faq
(
    id bigint NOT NULL,
    question character varying(1000) COLLATE pg_catalog."default" NOT NULL,
    answer character varying(1000) COLLATE pg_catalog."default" NOT NULL,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    category_id bigint NOT NULL,
    CONSTRAINT faq_pkey PRIMARY KEY (id),
    CONSTRAINT faq_category_id_fkey FOREIGN KEY (category_id)
    REFERENCES public.category (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);
CREATE SEQUENCE faq_id_seq START 1;

CREATE TABLE IF NOT EXISTS public.algorithm
(
    id bigint NOT NULL,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    text character varying(1000) COLLATE pg_catalog."default" NOT NULL,
    faq_id bigint,
    CONSTRAINT algoritm_pkey PRIMARY KEY (id),
    CONSTRAINT algoritm_faq_id_fk FOREIGN KEY (faq_id)
    REFERENCES public.faq (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);
CREATE SEQUENCE algorithm_id_seq START 1;

CREATE TABLE IF NOT EXISTS public.example
(
    id bigint NOT NULL,
    precondition character varying(1000) COLLATE pg_catalog."default" NOT NULL,
    solution character varying(10000) COLLATE pg_catalog."default" NOT NULL,
    algorithm_id bigint,
    CONSTRAINT example_pkey PRIMARY KEY (id),
    CONSTRAINT example_algoritm_id_fq FOREIGN KEY (algorithm_id)
    REFERENCES public.algorithm (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);
CREATE SEQUENCE example_id_seq START 1;

CREATE TABLE IF NOT EXISTS public."user"
(
    id bigint NOT NULL,
    telegram_chat_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    full_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    department character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE user_id_seq START 1;

CREATE TABLE IF NOT EXISTS public.user_role
(
    id bigint NOT NULL,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    permissions jsonb NOT NULL,
    CONSTRAINT user_role_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE user_role_id_seq START 1;

CREATE TABLE IF NOT EXISTS public.users_roles
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT users_roles_role_id_fk FOREIGN KEY (role_id)
    REFERENCES public.user_role (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT users_roles_user_id_fk FOREIGN KEY (user_id)
    REFERENCES public."user" (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

