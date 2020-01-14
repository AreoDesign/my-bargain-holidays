CREATE TABLE public.offer
(
    id uuid NOT NULL,
    board_type character varying(255)NOT NULL COLLATE pg_catalog."default",
    code character varying(255) COLLATE pg_catalog."default",
    departure_time timestamp without time zone NOT NULL,
    duration integer NOT NULL,
    url text NOT NULL COLLATE pg_catalog."default",
    hotel_id uuid NOT NULL,
    CONSTRAINT offer_pkey PRIMARY KEY (id),
    CONSTRAINT offer_unq_constraint_url UNIQUE (url)
    CONSTRAINT fk_hotel_2_offer FOREIGN KEY(hotel_id)
        REFERENCES public.hotel (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.offer OWNER to tester;
GRANT ALL ON TABLE public.offer TO tester;