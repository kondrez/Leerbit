drop table if exists vraag;
drop table if exists score;
drop table if exists user;
drop table if exists leerling;
drop table if exists vak;

CREATE TABLE leerling (
    leerling_nummer INT(5) NOT NULL,
    voor_naam VARCHAR(20),
    achter_naam VARCHAR(20),
    leeftijd INT(2),
    PRIMARY KEY (leerling_nummer)
);

CREATE TABLE vak (
    vak_naam VARCHAR(30) NOT NULL,
    hoeveelheid_vragen INT(2),
    PRIMARY KEY (vak_naam)
);

CREATE TABLE vraag (
    vraag_nummer INT(2) NOT NULL,
    vak_naam VARCHAR(30) NOT NULL,
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

CREATE TABLE score (
    leerling_nummer INT(5) NOT NULL,
    vak_naam VARCHAR(30) NOT NULL,
    aantal_goed INT(2),
    PRIMARY KEY (leerling_nummer , vak_naam),
    FOREIGN KEY (vak_naam)
        REFERENCES vak (vak_naam),
    FOREIGN KEY (leerling_nummer)
        REFERENCES leerling (leerling_nummer)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE user (
	id int(5) NOT NULL,
	username varchar(25) NOT NULL,
	password varchar(30) NOT NULL,
    primary key (id)
);

insert into leerling values 
('1','marko','rodriques','13'),
('2','eddy','al bazze','15'),
('3','Daniel ','Bleeker','14'),
('4','cristiaan','calf','63'),
('5','lucas','dekker','14');

insert into vak values 
('wiskunde-A1','2'),
('nederlands-A1','4');

insert into score values 
('2','wiskunde-A1','1'),
('3','wiskunde-A1','2');

insert into vraag values 
('1','wiskunde-A1','2+5=?','2','4','7','4','3'),
('2','wiskunde-A1','2*4=?','1','4','7','8','4');

insert into user values 
('1','admin','admin');