CREATE TABLE public.requestor
(
    id uuid NOT NULL,
    is_active boolean NOT NULL,
    login character varying(255)COLLATE pg_catalog."default" NOT NULL,
    password character varying(30)COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT requestor_pkey PRIMARY KEY(id),
    CONSTRAINT requestor_unq_login UNIQUE(login)
)

TABLESPACE pg_default;

ALTER TABLE public.requestor OWNER to tester;
GRANT ALL ON TABLE public.requestor TO tester;