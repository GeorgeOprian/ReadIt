create schema read_it;

DROP TABLE IF EXISTS read_it.JUDET CASCADE;
DROP TABLE IF EXISTS read_it.LOCALITATE CASCADE;
DROP TABLE IF EXISTS read_it.ADRESA cascade;

create table read_it.JUDET
(
  id_judet          INTEGER not null,
  nume              VARCHAR(25) not null
)
;

alter table read_it.JUDET
add constraint JUD_ID_PK primary key (id_judet);

create index JUD_NUME_IX on read_it.JUDET (nume);

create table read_it.LOCALITATE
(
  id_localitate     INTEGER not null,
  nume              VARCHAR(25) not null,
  id_judet          INTEGER not null
)
;

alter table read_it.LOCALITATE
add constraint LOC_ID_PK primary key (id_localitate);

alter table read_it.LOCALITATE
  add constraint LOC_JUD_FK foreign key (id_judet)
  references read_it.JUDET (id_judet);

create index LOC_NUME_IX on read_it.LOCALITATE (nume);


create table read_it.ADRESA
(
  id_adresa         INTEGER not null,
  strada            VARCHAR(40) not null,
  numar             INTEGER,
  bloc              VARCHAR(10),
  scara             VARCHAR(5),
  numar_apartament  INTEGER,
  id_localitate     INTEGER not null
)
;

alter table read_it.ADRESA
add constraint ADR_ID_PK primary key (id_adresa);

alter table read_it.ADRESA
  add constraint ADR_LOC_FK foreign key (id_localitate)
  references read_it.LOCALITATE (id_localitate);

create index ADR_STRADA_IX on read_it.ADRESA (strada);

alter table read_it.ADRESA
  add constraint ADR_UNIQUE UNIQUE (STRADA, NUMAR, BLOC, SCARA, NUMAR_APARTAMENT);


DROP SEQUENCE IF EXISTS read_it.LOCALITATE_SEQ;
DROP SEQUENCE IF EXISTS read_it.JUDET_SEQ;

CREATE SEQUENCE read_it.JUDET_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE read_it.LOCALITATE_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE read_it.ADRESA_SEQ START WITH 1 INCREMENT BY 1;

INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Alba');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Arad');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Argeș');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Bacău');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Bihor');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Bistrița-Năsăud');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Botoșani');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Brașov');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Brăila');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Buzău');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Caraș-Severin');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Călărași');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Cluj');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Constanța');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Covasna');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Dâmbovița');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Dolj');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Galați');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Giurgiu');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Gorj');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Harghita');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Hunedoara');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Ialomița');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Iași');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Ilfov');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Maramureș');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Mehedinți');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Mureș');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Neamț');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Olt');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Prahova');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Satu Mare');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Sălaj');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Sibiu');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Suceava');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Teleorman');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Timiș');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Tulcea');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Vaslui');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Vâlcea');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Vrancea');
INSERT INTO read_it.JUDET VALUES (nextval('JUDET_SEQ'), 'Bucuresti');

INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Alba Iulia', 1);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Aiud', 1);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Blaj', 1);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Cugir', 1);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Sebeș', 1);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Arad', 2);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Ineu', 2);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Lipova', 2);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Chișineu-Criș', 2);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Curtici', 2);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Pitești', 3);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Mioveni', 3);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Curtea de Argeș', 3);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Câmpulung', 3);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Topoloveni', 3);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Bacău', 4);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Onești', 4);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Moinesti', 4);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Comănești', 4);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Târgu Ocna', 4);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Oradea', 5);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Salonta', 5);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Beiuș', 5);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Marghita', 5);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Valea lui Mihai', 5);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Bistrița', 6);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Năsăud', 6);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Beclean', 6);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Sângeorz-Băi', 6);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Teaca', 6);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Botosani', 7);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Dorohoi', 7);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Saveni', 7);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Flămânzi', 7);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Brasov', 8);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Fagaras', 8);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Codlea', 8);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Victoria', 8);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Braila', 9);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Faurei', 9);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Ianca', 9);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Insuratei', 9);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Saveni', 7);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Flămânzi', 7);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Brasov', 8);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Fagaras', 8);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Codlea', 8);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Victoria', 8);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Chișineu-Criș', 9);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Faurei', 9);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Ianca', 9);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Insuratei', 9);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Buzau', 10);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Ramnicu Sarat', 10);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Nehoiu', 10);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Pătârlagele', 10);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Reșița', 11);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Caransebeș', 11);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Băile Herculane', 11);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Oțelu Roșu', 11);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Călărași', 12);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Oltenița', 12);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Lehliu Gară', 12);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Budesti', 12);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Cluj-Napoca', 13);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Turda', 13);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Dej', 13);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Gherla', 13);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Constanta', 14);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Mangalia', 14);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Medgidia', 14);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Navodari', 14);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Sfantu Gheorghe', 15);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Targu Secuiesc', 15);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Covasna', 15);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Baraolt', 15);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Targoviste', 16);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Moreni', 16);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Pucioasa', 16);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Gaesti', 16);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Craiova', 17);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Calafat', 17);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Bechet', 17);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Filiasi', 17);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Galati', 18);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Tecuci', 18);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Targu Bujor', 18);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Beresti-Targu', 18);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Giurgiu', 19);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Bolintin-Vale', 19);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Mihailesti', 19);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Schitu', 19);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Targu Jiu', 20);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Rovinari', 20);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Motru', 20);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Miercurea Ciuc', 21);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Toplita', 21);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Gheorgeni', 21);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Deva', 22);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Hunedoara', 22);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Orastie', 22);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Slobozia', 23);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Urziceni', 23);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Fetești', 23);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Iasi', 24);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Pascani', 24);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Targu Frumos', 24);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Voluntari', 25);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Otopeni', 25);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Buftea', 25);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Baia Mare', 26);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Borsa', 26);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Sighetu Marmatiei', 26);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Orsova', 27);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Strehaia', 27);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Vanju Mare', 27);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Targu Mures', 28);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Reghin', 28);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Sighisoara', 28);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Piatra Neamt', 29);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Roman', 29);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Roznov', 29);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Slatina', 30);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Caracal', 30);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Ploiesti', 31);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Campina', 31);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Satu Mare', 32);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Carei', 32);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Simleu Silvaniei', 33);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Zalau', 33);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Sibiu', 34);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Medias', 34);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Suceava', 35);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Falticeni', 35);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Alexandria', 36);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Rosiorii de Verde', 36);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Timisoara', 37);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Lugoj', 37);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Tulcea', 38);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Mahmudia', 38);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Vaslui', 39);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Barlad', 39);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Ramnicu Valcea', 40);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Dragasani', 40);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Focsani', 41);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Adjud', 41);
INSERT INTO read_it.LOCALITATE VALUES (nextval('LOCALITATE_SEQ'), 'Bucuresti', 42);


commit;
