CREATE SCHEMA core;

CREATE TABLE core.yritys (
    id SERIAL PRIMARY KEY
);

CREATE TABLE core.toimisto (
    id SERIAL PRIMARY KEY,
    nimi VARCHAR(255) NOT NULL,
    kaupunki VARCHAR(255) NOT NULL,
    maa VARCHAR(255) NOT NULL,
    yritys_id INTEGER NOT NULL REFERENCES core.yritys(id)
);

CREATE TABLE core.tyontekija (
    id SERIAL PRIMARY KEY,
    nimi VARCHAR(255) NOT NULL,
    sahkoposti VARCHAR(255) NOT NULL,
    puhelinnumero VARCHAR(255) NOT NULL
);

CREATE TABLE core.osasto (
    id SERIAL PRIMARY KEY,
    nimi VARCHAR(255) NOT NULL,
    yhteyshenkilo_id INTEGER REFERENCES core.tyontekija(id),
    toimisto_id INTEGER REFERENCES core.toimisto(id)
);

CREATE TYPE core.huone_tyyppi AS ENUM ('neuvottelu', 'tyotila');

CREATE TABLE core.huone (
    id SERIAL PRIMARY KEY,
    nimi VARCHAR(255) NOT NULL,
    osasto_id INTEGER NOT NULL references core.osasto(id),
    tyyppi core.huone_tyyppi NOT NULL
);

CREATE TABLE core.tyontekija_huone (
    id SERIAL PRIMARY KEY,
    tyontekija_id INTEGER REFERENCES core.tyontekija(id),
    huone_id INTEGER REFERENCES core.huone(id),
    UNIQUE(tyontekija_id, huone_id)
);

CREATE TABLE core.osaaminen(
    id SERIAL PRIMARY KEY,
    osaaminen VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE core.tyontekija_osaaminen (
    id SERIAL PRIMARY KEY,
    tyontekija_id INTEGER NOT NULL references core.tyontekija(id),
    osaaminen_id INTEGER not null references core.osaaminen(id),
    UNIQUE (tyontekija_id, osaaminen_id)
);

CREATE TABLE core.asiakas (
    id SERIAL PRIMARY KEY,
    nimi VARCHAR(255) NOT NULL,
    puhelinnumero VARCHAR(255) NOT NULL,
    toimiala VARCHAR(255) NOT NULL
);

CREATE TABLE core.asiakas_osaamisvaatimus (
    id SERIAL PRIMARY KEY,
    asiakas_id INTEGER NOT NULL REFERENCES core.asiakas(id),
    osaaminen_id INTEGER NOT NULL REFERENCES core.osaaminen(id),
    UNIQUE (asiakas_id, osaaminen_id)
);

CREATE TABLE core.tyontekija_asiakas (
    id SERIAL PRIMARY KEY,
    tyontekija_id INTEGER NOT NULL REFERENCES core.tyontekija(id),
    asiakas_id INTEGER NOT NULL REFERENCES core.asiakas(id),
    UNIQUE (tyontekija_id, asiakas_id)
);

CREATE TABLE core.autopaikka (
    id SERIAL PRIMARY KEY,
    toimisto_id INTEGER NOT NULL REFERENCES core.toimisto(id),
    tyontekija_id INTEGER REFERENCES core.tyontekija(id)
);

-- Lisätään resursseja tietokantaan
INSERT INTO core.yritys (id) VALUES (DEFAULT);

INSERT INTO core.toimisto (nimi, kaupunki, maa, yritys_id) VALUES
    ('Toimisto 1', 'Helsinki', 'Suomi', 1),
    ('Toimisto 1', 'Tampere',  'Suomi', 1);

INSERT INTO core.tyontekija(nimi, sahkoposti, puhelinnumero) VALUES
    ('Henkilö 1', '1@example.com', '0401234567'),
    ('Henkilö 2', '2@example.com', '0401234567'),
    ('Henkilö 3', '3@example.com', '0401234567'),
    ('Henkilö 4', '4@example.com', '0401234567'),
    ('Henkilö 5', '5@example.com', '0401234567'),
    ('Henkilö 6', '6@example.com', '0401234567'),
    ('Henkilö 7', '7@example.com', '0401234567'),
    ('Henkilö 8', '8@example.com', '0401234567'),
    ('Henkilö 9', '9@example.com', '0401234567');

INSERT INTO core.osasto (nimi, yhteyshenkilo_id, toimisto_id) VALUES
    ('IT', 1, 1),
    ('Markkinointi', 2, 1),
    ('Myynti', 3, 1),
    ('IT', 4, 2);

INSERT INTO core.huone (nimi, osasto_id, tyyppi) VALUES
    ('Työhuone1', 1, 'tyotila'),
    ('Työhuone2', 1, 'tyotila'),
    ('Neuvotteluhuone', 1, 'neuvottelu'),
    ('Työhuone3', 2, 'tyotila'),
    ('Työhuone4', 3, 'tyotila'),
    ('Työhuone5', 4, 'tyotila'),
    ('Työhuone6', 4, 'tyotila');

INSERT INTO core.tyontekija_huone (tyontekija_id, huone_id) VALUES
    (1, 1),
    (2, 4),
    (3, 5),
    (4, 6),
    (5, 2),
    (6, 7),
    (8, 1),
    (9, 6);

INSERT INTO core.osaaminen (osaaminen) VALUES ('React'), ('Kotlin'), ('GraphQL'), ('SQL'), ('Docker');

INSERT INTO core.tyontekija_osaaminen (tyontekija_id, osaaminen_id) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (2, 1),
    (2, 2),
    (3, 3),
    (4, 3),
    (5, 4),
    (5, 5),
    (6, 5),
    (7, 1),
    (7, 2),
    (7, 3),
    (7, 4),
    (7, 5),
    (8, 1),
    (9, 1);

INSERT INTO core.asiakas (nimi, puhelinnumero, toimiala) VALUES
    ('Asiakas1', '0401234567', 'IT'),
    ('Asiakas2', '0401234567', 'Markkinointi'),
    ('Asiakas3', '0401234567', 'Terveydenhuolto'),
    ('Asiakas4', '0401234567', 'IT');

INSERT INTO core.asiakas_osaamisvaatimus (asiakas_id, osaaminen_id) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 4),
    (2, 5),
    (3, 1),
    (3, 3),
    (3, 4),
    (4, 1),
    (4, 2),
    (4, 3),
    (4, 5);

INSERT INTO core.tyontekija_asiakas(tyontekija_id, asiakas_id) VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (2, 4),
    (3, 1),
    (3, 4),
    (4, 1),
    (4, 2),
    (5, 1),
    (6, 3),
    (7, 4),
    (8, 1),
    (9, 4);

INSERT INTO core.autopaikka(toimisto_id, tyontekija_id) VALUES (1, 1), (1, 2), (1, 3), (1, 5), (1, 7), (2, 4), (2, 6), (2, 9), (2, null), (2, null);