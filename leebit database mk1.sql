create schema leerbit;

drop table if exists leerling;
CREATE TABLE Leerling (
    leerling_nummer INT(5) NOT NULL,
    voor_naam VARCHAR(20),
    achter_naam VARCHAR(20),
    leeftijd INT(2),
    PRIMARY KEY (leerling_nummer)
);


drop table if exists vak;
CREATE TABLE vak (
    vak_naam VARCHAR(10) NOT NULL,
    hoeveelheid_vragen INT(2),
    PRIMARY KEY (vak_naam)
);

drop table if exists vragen;
CREATE TABLE vraag (
    vraag_nummer INT(2) NOT NULL,
    vak_naam VARCHAR(10) NOT NULL,
    opdracht VARCHAR(45),
    antwoord1 VARCHAR(20),
    antwoord2 VARCHAR(20),
    antwoord3 VARCHAR(20),
    antwoord4 VARCHAR(20),
    juiste_antwoord INT(1),
    PRIMARY KEY (vraag_nummer , vak_naam),
    FOREIGN KEY (vak_naam)
        REFERENCES vak (vak_naam)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

drop table if exists score;
CREATE TABLE score (
    leerling_nummer INT(5) NOT NULL,
    vak_naam VARCHAR(10) NOT NULL,
    aantal_goed INT(2),
    PRIMARY KEY (leerling_nummer , vak_naam),
    FOREIGN KEY (vak_naam)
        REFERENCES vak (vak_naam),
    FOREIGN KEY (leerling_nummer)
        REFERENCES leerling (leerling_nummer)
        ON DELETE NO ACTION ON UPDATE CASCADE
);


