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
	vak_nummer int(1) NOT NULL,
    vak_naam VARCHAR(30) NOT NULL,
    hoeveelheid_vragen INT(2),
    PRIMARY KEY (vak_nummer)
);

CREATE TABLE vraag (
    vraag_nummer INT(2) NOT NULL,
    vak_nummer int(1) NOT NULL,
    opdracht VARCHAR(45),
    antwoord1 VARCHAR(20),
    antwoord2 VARCHAR(20),
    antwoord3 VARCHAR(20),
    antwoord4 VARCHAR(20),
    juiste_antwoord VARCHAR(1),
    PRIMARY KEY (vraag_nummer , vak_nummer),
    FOREIGN KEY (vak_nummer)
        REFERENCES vak (vak_nummer)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE score (
    leerling_nummer INT(5) NOT NULL,
    vak_nummer int(1) NOT NULL,
    aantal_goed INT(2),
    PRIMARY KEY (leerling_nummer , vak_nummer),
    FOREIGN KEY (vak_nummer)
        REFERENCES vak (vak_nummer),
    FOREIGN KEY (leerling_nummer)
        REFERENCES leerling (leerling_nummer)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE user (
	id int(5) NOT NULL,
	username varchar(25) NOT NULL,
	password int(100) NOT NULL,
    primary key (id)
);

insert into leerling values 
('1','marko','rodriques','13'),
('2','eddy','al bazze','15'),
('3','Daniel ','Bleeker','14'),
('4','cristiaan','calf','63'),
('5','lucas','dekker','14');

insert into vak values 
('1','rekenen','15'),
('2','geschiedenis','15');

insert into score values 
('2','1','1'),
('3','1','2');

insert into vraag values 
('1','1','2+5=?','2','4','7','4','c'),
('2','1','8*3=?','24','11','5','58','a'),
('3','1','5-5=?','10','0','9','3','b'),
('4','1','9*6=?','15','3','54','48','c'),
('5','1','20:5=?','100','15','25','4','d'),
('6','1','7+7=?','0','49','13','14','d'),
('7','1','8+5=?','15','40','3','13','d'),
('8','1','6+5=?','11','1','30','16','a'),
('9','1','42-18=?','33','24','60','28','b'),
('10','1','60-15=?','45','65','50','40','a'),
('11','1','80:10=?','70','90','8','800','c'),
('12','1','56:8=?','48','64','50','7','d'),
('13','1','18*2=?','36','16','34','14','a'),
('14','1','25-13=?','12','13','38','35','a'),
('15','1','10*10=?','1','10','100','20','c'),
('1','2','Start jaar WWI=?','1914','1939','1917','1945','a'),
('2','2','Start jaar WWII=?','1939','1945','1918','1925','a'),
('3','2','Wie heeft 5 keer het WK gewonnen=?','Duitsland','Frankrijk','Brazilie','Nederland','c'),
('4','2','Wie heeft Amerika ontdekt=?','Vincent van Gogh','Columbus','Hitler','Stalin','b'),
('5','2','Oprichtings jaar VOC=?','1666','1712','1612','1559','c'),
('6','2','Eind jaar slavernij=?','1492','1863','1526','1777','b'),
('7','2','Andere naam Grote oorlog=?','WWI',' WWII','WWIII','Koude oorlog','a'),
('8','2','Start datum koude oorlog=?','1955','1969','1958','1947','d'),
('9','2','Leider van de Nazi\'s=?','Hitler','Stalin','Trump','Fuhrer','a'),
('10','2','Startdatum vietnaamsoorlog=?','1966','1954','1955','1956','c'),
('11','2','Periode van het jaar 100 tot 500=?','Prehistorie','Moderne tijd','Middeleeuwen','Gouden eeuw','c'),
('12','2','Wie schilderde De nachtwacht=?','Vincent van Gogh',' Rembrandt van Rijn','Pablo Picasso','Salvador Dali','b'),
('13','2','Wie heeft de Mona Lisa geschilderd=?','Leonardo Da vinci',' Vincent van Gogh',' Pablo Picasso','Rembrandt  van Rijn','a'),
('14','2','Hoe heette de vader van Anne Frank=?',' Bert',' Otto',' Jan',' Motto','b'),
('15','2','Wie was de eerste vrouwelijke huisarts=?','Aletta Jacobs',' Mees Berg',' Johanna Jo','Ashe Best','a');

insert into user values 
('1','admin','92668751');