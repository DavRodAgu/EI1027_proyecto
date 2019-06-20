insert into Cliente values ('64456345H', 'Vilhelmina Emanuele', 'vemanuele0@blogspot.com', 'F', '09/20/1992');
insert into Cliente values ('35270759Y', 'Cris Dugue', 'cdugue1@myspace.com', 'F', '08/29/1988');
insert into Cliente values ('83372943J', 'Dorthea Broschke', 'dbroschke2@friendfeed.com', 'F', '03/14/1987');
insert into Cliente values ('48368048Z', 'Fanchette Cahani', 'fcahani3@go.com', 'F', '03/08/1988');
insert into Cliente values ('20296118K', 'Gav Beals', 'gbeals4@usnews.com', 'M', '06/28/1985');
insert into Cliente values ('24070011M', 'Casey Espley', 'cespley5@timesonline.co.uk', 'M', '08/10/1984');
insert into Cliente values ('19127627E', 'Harv Abramov', 'habramov6@sun.com', 'M', '06/12/1986');
insert into Cliente values ('11172815J', 'Robinet Jopling', 'rjopling7@ucla.edu', 'F', '06/06/1990');
insert into Cliente values ('21270631R', 'Trista Cordero', 'tcordero8@constantcontact.com', 'F', '11/21/1986');
insert into Cliente values ('32453081N', 'Betsy Reide', 'breide9@myspace.com', 'F', '02/17/1982');
insert into Cliente values ('58502831P', 'Olivero Killimister', 'okillimistera@mozilla.com', 'M', '07/01/1995');
insert into Cliente values ('76936785Z', 'Netty Kilfeather', 'nkilfeatherb@scientificamerican.com', 'F', '04/12/1986');
insert into Cliente values ('75558479Y', 'Trudie Kemball', 'tkemballc@bbb.org', 'F', '07/18/1984');
insert into Cliente values ('87613518D', 'Livvyy Iacomettii', 'liacomettiid@slashdot.org', 'F', '01/06/1984');
insert into Cliente values ('58348212H', 'Torrin Esplin', 'tespline@eepurl.com', 'M', '10/14/1987');

insert into Login values ('64456345H', '64456345H', 'cliente');
insert into Login values ('35270759Y', '35270759Y', 'cliente');
insert into Login values ('83372943J', '83372943J', 'cliente');
insert into Login values ('48368048Z', '48368048Z', 'cliente');
insert into Login values ('20296118K', '20296118K', 'cliente');
insert into Login values ('24070011M', '24070011M', 'cliente');
insert into Login values ('19127627E', '19127627E', 'cliente');
insert into Login values ('11172815J', '11172815J', 'cliente');
insert into Login values ('21270631R', '21270631R', 'cliente');
insert into Login values ('32453081N', '32453081N', 'cliente');
insert into Login values ('58502831P', '58502831P', 'cliente');
insert into Login values ('76936785Z', '76936785Z', 'cliente');
insert into Login values ('75558479Y', '75558479Y', 'cliente');
insert into Login values ('87613518D', '87613518D', 'cliente');
insert into Login values ('58348212H', '58348212H', 'cliente');


insert into Login values ('manel', 'manel', 'administrador');


alter sequence tipoactividad_idtipoactividad_seq restart;

INSERT INTO "tipoactividad" VALUES
    (default,'actividades aeronáuticas','bajo'),
    (default,'actividades aeronáuticas','medio'),
    (default,'actividades aeronáuticas','alto'),
    (default,'actividades aeronáuticas','extremo'),
    (default,'actividades subacuáticas','bajo'),
    (default,'actividades subacuáticas','medio'),
    (default,'actividades subacuáticas','alto'),
    (default,'actividades subacuáticas','extremo'),
    (default,'automovilismo','bajo'),
    (default,'automovilismo','medio'),
    (default,'automovilismo','alto'),
    (default,'automovilismo','extremo'),
    (default,'boxeo','bajo'),
    (default,'boxeo','medio'),
    (default,'boxeo','alto'),
    (default,'boxeo','extremo'),
    (default,'caza','bajo'),
    (default,'caza','medio'),
    (default,'caza','alto'),
    (default,'caza','extremo'),
    (default,'ciclismo','bajo'),
    (default,'ciclismo','medio'),
    (default,'ciclismo','alto'),
    (default,'ciclismo','extremo'),
    (default,'deportes de hielo','bajo'),
    (default,'deportes de hielo','medio'),
    (default,'deportes de hielo','alto'),
    (default,'deportes de hielo','extremo'),
    (default,'deportes de invierno','bajo'),
    (default,'deportes de invierno','medio'),
    (default,'deportes de invierno','alto'),
    (default,'deportes de invierno','extremo'),
    (default,'esgrima','bajo'),
    (default,'esgrima','medio'),
    (default,'esgrima','alto'),
    (default,'esgrima','extremo'),
    (default,'espeleología','bajo'),
    (default,'espeleología','medio'),
    (default,'espeleología','alto'),
    (default,'espeleología','extremo'),
    (default,'esquí naútico','bajo'),
    (default,'esquí naútico','medio'),
    (default,'esquí naútico','alto'),
    (default,'esquí naútico','extremo'),
    (default,'fitness y actividades dirigidas','bajo'),
    (default,'fitness y actividades dirigidas','medio'),
    (default,'fitness y actividades dirigidas','alto'),
    (default,'fitness y actividades dirigidas','extremo'),
    (default,'gimnasia','bajo'),
    (default,'gimnasia','medio'),
    (default,'gimnasia','alto'),
    (default,'gimnasia','extremo'),
    (default,'hockey','bajo'),
    (default,'hockey','medio'),
    (default,'hockey','alto'),
    (default,'hockey','extremo'),
    (default,'judo','bajo'),
    (default,'judo','medio'),
    (default,'judo','alto'),
    (default,'judo','extremo'),
    (default,'karate','bajo'),
    (default,'karate','medio'),
    (default,'karate','alto'),
    (default,'karate','extremo'),
    (default,'kickboxing','bajo'),
    (default,'kickboxing','medio'),
    (default,'kickboxing','alto'),
    (default,'kickboxing','extremo'),
    (default,'luchas olímpicas','bajo'),
    (default,'luchas olímpicas','medio'),
    (default,'luchas olímpicas','alto'),
    (default,'luchas olímpicas','extremo'),
    (default,'montaña y escalada','bajo'),
    (default,'montaña y escalada','medio'),
    (default,'montaña y escalada','alto'),
    (default,'montaña y escalada','extremo'),
    (default,'motociclismo','bajo'),
    (default,'motociclismo','medio'),
    (default,'motociclismo','alto'),
    (default,'motociclismo','extremo'),
    (default,'motonáutica','bajo'),
    (default,'motonáutica','medio'),
    (default,'motonáutica','alto'),
    (default,'motonáutica','extremo'),
    (default,'multiaventura','bajo'),
    (default,'multiaventura','medio'),
    (default,'multiaventura','alto'),
    (default,'multiaventura','extremo'),
    (default,'orientación','bajo'),
    (default,'orientación','medio'),
    (default,'orientación','alto'),
    (default,'orientación','extremo'),
    (default,'otros','bajo'),
    (default,'otros','medio'),
    (default,'otros','alto'),
    (default,'otros','extremo'),
    (default,'pentatlon moderno','bajo'),
    (default,'pentatlon moderno','medio'),
    (default,'pentatlon moderno','alto'),
    (default,'pentatlon moderno','extremo'),
    (default,'piragüismo','bajo'),
    (default,'piragüismo','medio'),
    (default,'piragüismo','alto'),
    (default,'piragüismo','extremo'),
    (default,'salvamento y socorrismo','bajo'),
    (default,'salvamento y socorrismo','medio'),
    (default,'salvamento y socorrismo','alto'),
    (default,'salvamento y socorrismo','extremo'),
    (default,'semana europea del deporte','bajo'),
    (default,'semana europea del deporte','medio'),
    (default,'semana europea del deporte','alto'),
    (default,'semana europea del deporte','extremo'),
    (default,'squash','bajo'),
    (default,'squash','medio'),
    (default,'squash','alto'),
    (default,'squash','extremo'),
    (default,'surf','bajo'),
    (default,'surf','medio'),
    (default,'surf','alto'),
    (default,'surf','extremo'),
    (default,'taekwondo','bajo'),
    (default,'taekwondo','medio');
INSERT INTO "tipoactividad" VALUES
    (default,'taekwondo','alto'),
    (default,'taekwondo','extremo');


