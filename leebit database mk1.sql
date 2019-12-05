drop table leerling;
create table Leerling(
leerling_nummer int(5) not null,
voor_naam varchar(20),
achter_naam varchar(20),
leeftijd int (2),
primary key (leerling_nummer)
);


drop table vak;
create table vak(
vak_naam varchar(10) not null,
hoeveelheid_vragen int(2),
primary key (vak_naam));

drop table vragen;
create table vraag(
vraag_nummer int(2) not null,
vak_naam varchar(10) not null,
opdracht varchar(45),
antwoord1 varchar(20),
antwoord2 varchar(20),
antwoord3 varchar(20),
antwoord4 varchar(20),
juiste_antwoord int(1),
primary key (vraag_nummer, vak_naam),
foreign key (vak_naam) references vak (vak_naam)
on delete no action
on update cascade);

drop table score;
create table score(
leerling_nummer int(5) not null,
vak_naam varchar(10) not null,
aantal_goed int(2),
primary key (leerling_nummer, vak_naam),
foreign key (vak_naam) references vak (vak_naam),
foreign key (leerling_nummer) references leerling (leerling_nummer)
on delete no action
on update cascade);


