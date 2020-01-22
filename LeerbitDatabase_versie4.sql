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
	username int(100) NOT NULL,
	password int(100) NOT NULL,
    primary key (id)
);

insert into leerling values 
('1','Marko','Rodriques','13'),
('2','Eddy','Al Bazze','15'),
('3','Daniel','Bleeker','14'),
('4','Cristiaan','Calf','63'),
('5','Lucas','Dekker','14');

insert into vak values 
('1','rekenen','15'),
('2','geschiedenis','15'),
('3','engels','15'),
('4','aardrijkskunde','15'),
('5','nederlands','15');

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
('12','1','56:8=?','48','6','14','7','d'),
('13','1','18*2=?','36','16','34','14','a'),
('14','1','25-13=?','12','13','38','35','a'),
('15','1','10*10=?','1','10','100','20','c'),
('1','2','Start jaar WWI=?','1914','1939','1917','1945','a'),
('2','2','Start jaar WWII=?','1939','1945','1918','1925','a'),
('3','2','Wie won 5 keer het WK?','DE','FR','Braz','NL','c'),
('4','2','Wie ontdekte Amerika?','van Gogh','colomb','Hitler','Stalin','b'), 
('5','2','Oprichtings jaar VOC?','1666','1712','1612','1559','c'),
('6','2','Eind jaar slavernij?','1492','1863','1526','1777','b'),
('7','2','Andere naam Grote oorlog?','WWI','WWII','WWIII','\'nam','a'),
('8','2','Start datum koude oorlog?','1955','1969','1958','1947','d'),
('9','2','Leider van de Nazi\'s?','Hitler','Stalin','Trump','Fuhrer','a'),
('10','2','Start vietnaamsoorlog?','1966','1954','1955','1956','c'),
('11','2','napoleon was keizer van?','DE','NL','FR','RU','c'),
('12','2','De nachtwacht is van?','van Gogh','Rembrandt','Picasso','Dali','b'),
('13','2','Mona lisa is van?','Da vinci','van Gogh','Picasso','Rembrandt','a'),
('14','2','de vader van Anne Frank?',' Bert',' Otto',' Jan',' Motto','b'),
('15','2','1e vrouwelijke huisarts?','Aletta','Berg','Johanna','Ashe','a'),
('1','3','huis in het engels','bed','house','heist','home','b'),
('2','3','bad in het engels','bath','bed','toilet','couch','a'),
('3','3','bezem in het engels','bord','plank','broom','boat','c'),
('4','3','tijd in het engels','time','tooth','table','barn','a'),
('5','3','boos in het engels','bees','born','barn','angry','d'),
('6','3','monitor in nederlands','tafel','trap','beeldscherm','toetsenbord','c'),
('7','3','bag in nederlands','tas','krat','fiets','scooter','a'),
('8','3','cup in nederlands','bord','mes','kop','schotel','c'),
('9','3','knife in nederlands','mes','zwaard','schep','hakbijl','a'),
('10','3','book in nederlands','brood','boek','bad','ketting','b'),
('11','3','handschoen in het engels','guard','lighter','glove','shoe','c'),
('12','3','kussen in het engels','powder','bucket','bottle','pillow','d'),
('13','3','helm in het engels','helmet','kneepads','bucket','match','a'),
('14','3','kast in het engels','speaker','box','closet','bike','c'),
('15','3','bank in het engels','couch','chair','bed','bath','a'),
('1','4','kleinste continent','Australie','Amerika','Europa','Azie','a'),
('2','4','grootste continent','Azie','Europa','Amerika','Afrika','a'),
('3','4','Grootste land ter wereld','Rusland','VS','China','NL','a'),
('4','4','Grootste rivier in Egypte','Nijl','Rijn','Ob','Amoer','a'),
('5','4','Grootste berg ter wereld','Cho Oyu','K2','Everest','Lhotse','c'),
('6','4','meest gesproken taal','Eng','frans','NL','Spa','a'),
('7','4','Hoofdstad Spanje','Madrid','Barca','Cadiz','Bilbao','a'),
('8','4','Meest bevolkte land','Engeland','india','VS','China','d'),
('9','4','Hoofdstad NL','Den Haag','Amsterdam','Rotterdam','Utrecht','b'),
('10','4','Hoofdstad Ghana ','Cairo','Accra','London','Damascus','b'),
('11','4','Hoofdstad UK','London','Washington','Bejing','Wales','a'),
('12','4','Hoofdstad VS','New York','Washington','Alaska','Mexico','b'),
('13','4','Hoofdstad Belgie','Amsterdam','Den Haag','Berlijn','Brussel','d'),
('14','4','Hoofdstad China','Hongkong','Japan','Bejing','Tokyo','c'),
('15','4','hoofdstad kroatie','Zagreb','NL','Belgie','Luxemburg','a'),
('1','5','Ik-vorm van lopen','loop','liepen','liep','loopte','a'),
('2','5','Vt ik-vorm van zwemmen','Zwemde','Zwommen','Zwem','Zwom','d'),
('3','5','Vt hij-vorm van rennen','rennen','rende','ronde','rande','b'),
('4','5','Wij-vorm van duiken','doken','duikten','duiken','duikte','c'),
('5','5','Vt wij-vorm schieten','schiet','schieten','schoot','schoten','d'),
('6','5','Vt hij-vorm knippen','knopte','knipten','knipte','knop','c'),
('7','5','Vt hij-vorm slaan','sloeg','slaande','sloegen','slagen','a'),
('8','5','Vtd deelwoord Sprinten','sprint','spront','gesprint','spring','c'),
('9','5','Vtd deelwoord zuigen','Zoog','Gezuigd','Verzogen','zuigde','b'),
('10','5','Ik-vorm van kruipen','kroop','kruip','kropen','kruipte','b'),
('11','5','Wij-vorm Vt van Koppen','Kopten','Koppten','Koptten','Kopte','a'),
('12','5','Hij-vorm vt van zitten','zaten','zitte','zat','zaten','c'),
('13','5','Vt hij vorm van pakken','pakte','pakken','pakten','pikte','a'),
('14','5','Ik vorm van gooien','gooide','gooi','gooien','gooiden','c'),
('15','5','Vt wij-vorm van juichen','Juichden','juichen','juicht','joogden','a');

insert into user values 
('1','92668751','92668751');