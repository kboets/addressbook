CREATE SEQUENCE city_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;


CREATE TABLE city
(
    id         INT PRIMARY KEY DEFAULT nextval('city_id_seq'),
    name       TEXT NOT NULL,
    zip_code   TEXT NOT NULL,
    country_code TEXT NOT NULL
);

CREATE TABLE street_names
(
    city_id INT,
    name    TEXT
);


ALTER TABLE street_names
    ADD CONSTRAINT fk_streetnames_city
        FOREIGN KEY (city_id)
            REFERENCES city(id);

