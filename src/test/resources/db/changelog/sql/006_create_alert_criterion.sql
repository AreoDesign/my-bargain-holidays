CREATE TABLE public.alert_criterion
(
    countries character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(255)COLLATE pg_catalog."default" NOT NULL,
    holiday_end date NOT NULL,
    holiday_start date NOT NULL,
    is_active boolean NOT NULL,
    min_hotel_standard numeric(2, 1)NOT NULL,
    price_max integer NOT NULL,
    search_criterion_id integer NOT NULL,
    CONSTRAINT alert_criterion_pkey PRIMARY KEY(search_criterion_id),
    CONSTRAINT fk_alert_criterion_2_search_criterion FOREIGN KEY(search_criterion_id)
        REFERENCES public.search_criterion(id)MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.alert_criterion OWNER to tester;
GRANT ALL ON TABLE public.alert_criterion TO tester;