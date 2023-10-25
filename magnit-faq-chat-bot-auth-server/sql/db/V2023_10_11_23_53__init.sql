CREATE TABLE IF NOT EXISTS public."user"
(
    id bigint NOT NULL,
    telegram_id bigint,
    full_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    department character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE user_id_seq START 1;

CREATE TABLE IF NOT EXISTS public.user_role
(
    id bigint NOT NULL,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
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

