CREATE TABLE public.hotel
(
    id uuid NOT NULL,
    code character varying(255)COLLATE pg_catalog."default",
    country character varying(255)NOT NULL COLLATE pg_catalog."default",
    name character varying(255)NOT NULL COLLATE pg_catalog."default",
    opinion numeric(2, 1),
    standard numeric(2, 1)NOT NULL,
    CONSTRAINT hotel_pkey PRIMARY KEY(id),
    CONSTRAINT hotel_unq_constraint_name_country UNIQUE(name, country)
)

TABLESPACE pg_default;

ALTER TABLE public.hotel OWNER to tester;
GRANT ALL ON TABLE public.hotel TO tester;