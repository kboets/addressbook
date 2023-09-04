CREATE TABLE address(
                        id BIGSERIAL primary key,
                        street VARCHAR NOT NULL,
                        house_number VARCHAR(20) NOT NULL,
                        box VARCHAR(5),
                        zip_code VARCHAR(20) NOT NULL,
                        city VARCHAR NOT NULL
);

ALTER TABLE address ADD COLUMN country_id INTEGER;

ALTER TABLE address
    ADD CONSTRAINT fk_country_address
        FOREIGN KEY (country_id)
            REFERENCES country(id);

