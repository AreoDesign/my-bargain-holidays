CREATE TABLE public.search_criterion
(
    id integer NOT NULL DEFAULT nextval('search_criterion_id_seq'::regclass),
    adults_birth_dates character varying(255)COLLATE pg_catalog."default" NOT NULL,
    board_types character varying(255)COLLATE pg_catalog."default" NOT NULL,
    children_birth_dates character varying(255)COLLATE pg_catalog."default",
    countries character varying(255)COLLATE pg_catalog."default" NOT NULL,
    creation_time timestamp without time zone NOT NULL,
    departure_cities character varying(255)COLLATE pg_catalog."default" NOT NULL,
    departure_date_from date NOT NULL,
    departure_date_to date NOT NULL,
    is_active boolean NOT NULL,
    min_hotel_std numeric(2, 1)NOT NULL,
    stay_length integer NOT NULL,
    requestor_id uuid NOT NULL,
    CONSTRAINT search_criterion_pkey PRIMARY KEY(id),
    CONSTRAINT fk_search_criterion_2_requstor FOREIGN KEY(requestor_id)
        REFERENCES public.requestor(id)MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT search_criterion_min_hotel_std_check CHECK(min_hotel_std >= 1 AND min_hotel_std <= 5),
    CONSTRAINT search_criterion_stay_length_check CHECK(stay_length <= 30 AND stay_length >= 3)
)

TABLESPACE pg_default ;

ALTER TABLE public.search_criterion OWNER to tester;
GRANT ALL ON TABLE public.search_criterion TO tester;