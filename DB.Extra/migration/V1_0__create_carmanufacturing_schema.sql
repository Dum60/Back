CREATE TABLE IF NOT EXISTS Company
(
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name         VARCHAR(255),
    creationdate date,
    CONSTRAINT pk_company PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Model
(
    id        INTEGER generated always as identity
        constraint "CarModel_pk"
            primary key,
    name      VARCHAR(255),
    length    INTEGER NOT NULL,
    width     INTEGER NOT NULL,
    body      VARCHAR(255),
    CompanyId INTEGER
        constraint "CarModel_CarCompany_ID_fk"
            references company
);

ALTER TABLE Model
    ADD CONSTRAINT FK_MODEL_ON_COMPANYID FOREIGN KEY (CompanyId) REFERENCES Company (id);