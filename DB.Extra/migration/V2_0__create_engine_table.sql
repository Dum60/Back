CREATE TABLE IF NOT EXISTS Engine
(
    id        INTEGER NOT NULL,
    name      varchar(255),
    volume    INTEGER,
    cylinders INTEGER,
    height    INTEGER,
    ModelId   INTEGER,
    CONSTRAINT pk_engine PRIMARY KEY (id)
);

ALTER TABLE Engine
    ADD CONSTRAINT FK_ENGINE_ON_MODELID FOREIGN KEY (ModelId) REFERENCES Model (id)