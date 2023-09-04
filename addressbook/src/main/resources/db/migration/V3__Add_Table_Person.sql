CREATE TABLE person(
                       id BIGSERIAL primary key,
                       name VARCHAR NOT NULL,
                       first_name VARCHAR NOT NULL,
                       birth_date DATE NOT NULL,
                       phone_number VARCHAR,
                       mobile_phone  VARCHAR,
                       email VARCHAR NOT NULL
);

ALTER TABLE person ADD COLUMN main_address_id INTEGER;

ALTER TABLE person
    ADD CONSTRAINT fk_address_person
        FOREIGN KEY (main_address_id)
            REFERENCES address(id);

ALTER TABLE person
    ADD CONSTRAINT person_email_unique UNIQUE (email)

