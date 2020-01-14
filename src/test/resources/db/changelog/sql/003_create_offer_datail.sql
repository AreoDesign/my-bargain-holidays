CREATE TABLE public.offer_detail
(
    id bigint NOT NULL DEFAULT nextval('offer_detail_id_seq'::regclass),
    discount_price_per_person integer,
    standard_price_per_person integer NOT NULL,
    request_time timestamp without time zone NOT NULL,
    offer_id uuid NOT NULL,
    CONSTRAINT offer_detail_pkey PRIMARY KEY (id),
    CONSTRAINT offer_detail_unq_constraint_offer_id_request_time UNIQUE (offer_id, request_time)
    CONSTRAINT fk_offer_2_offer_detail FOREIGN KEY(offer_id)
        REFERENCES public.offer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.offer_detail OWNER to tester;
GRANT ALL ON TABLE public.offer_detail TO tester;