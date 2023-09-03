ALTER TABLE Model
    ADD height INTEGER;
ALTER TABLE Model
    ADD CONSTRAINT check_body
        check ((body)::text = ANY
            ((ARRAY ['sedan'::character varying, 'coupe'::character varying, 'hatchback'::character varying, 'roadster'::character varying, 'SUV'::character varying, 'pickup'::character varying])::text[]))