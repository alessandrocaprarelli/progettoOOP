use progogg;
create table Campionato(
     Nome varchar(20) primary key,
     NrPartecipanti tinyint not null,
     Asta boolean not null,
     Pubblico boolean not null,
	Presidente varchar(20) not null references Utente(Nickname) on update cascade on delete no action
);

create table Regolamento(
     NomeCampionato varchar(20) primary key references Campionato(Nome) on update cascade on delete no action,
     GiornataInizio tinyint not null,
     GiornataFine tinyint not null,
     CreditiIniziali SmallInt unsigned not null,
     OrarioConsegna Tinyint unsigned not null,
     PrimaFascia tinyint unsigned,
     LargFascia tinyint unsigned,
     BonusCasa numeric(2,1) not null,
     ModDifesa tinyint not null
);

create table Fantasquadra(
     Nome varchar(20) primary key,
     NickUt varchar(20) not null,
     unique key(Nome, NickUt)
);

create table Utente(
     Nickname varchar(20) primary key,
     Password varchar(20),
     Nome varchar(20),
     Cognome varchar(20),
     Email varchar(30),
     TipoUtente char
);


create table Iscrizione(
     NomeSq varchar(20) references Fantasquadra(Nome) on update cascade on delete no action,
     Campionato varchar(20) references Campionato(Nome) on update cascade on delete no action,
     CreditiDisponibili smallint,
     primary key (NomeSq, Campionato)
);

create trigger Classifica
after insert on Iscrizione
for each row
insert into Classifica (NomeCampionato, NomeSq) values(NEW.Campionato, NEW.NomeSq);

create table CalciatoreAnno(
     ID int primary key ,
     Ruolo char(1) not null,
     Cognome varchar(20) not null,
     SqReale varchar(20) not null,
     Costo tinyint not null
     );

create table Tesseramento(
     IDcalcAnno int references CalciatoreAnno(ID) on update cascade on delete no action,
     NomeCampionato varchar(20) references Campionato(Nome) on update cascade on delete no action,
     NomeSq varchar(20) references Fantasquadra(Nome) on update cascade on delete no action,
     PrezzoPagato smallint not null,
     primary key(IDcalcAnno, NomeCampionato , NomeSq)
);


create table Voto(
     IDcalcAnno int references CalciatoreAnno(ID) on update cascade on delete no action,
     NrGioReale int,
     Voto numeric(2,1) unsigned not null,
     GolF tinyint unsigned not null,
     GolS tinyint unsigned not null,
     AutoG tinyint unsigned not null,
     RigF tinyint unsigned not null,
     RigS tinyint unsigned not null,
     RigP tinyint unsigned not null,
     Ass tinyint unsigned not null,
     Amm tinyint unsigned not null,
     Esp tinyint unsigned not null,
     primary key(IDcalcAnno, NrGioReale)
);

create table Formazione(
     IDcalcAnno int references CalciatoreAnno(ID) on update cascade on delete no action,
     IDpart int references Partita(ID) on update cascade on delete no action,
     NomeSq varchar(20) references Fantasquadra(Nome) on update cascade on delete no action,
     Pos tinyint unsigned not null,
     Ruolo char(1) not null,
     primary key(IDcalcAnno, IDpart)
);

create table GiornataAnno(
	NrGioReale tinyint unsigned primary key,
     DataInizio date not null,
     OraInizio time not null,
	DataFine date not null,
	OraFine time 

);


create table Giornata(
     ID int primary key auto_increment,
     NomeCampionato varchar(20) not null references Campionato(Nome) on update cascade on delete no action,
     NrGio tinyint unsigned not null,
     NrGioReale tinyint unsigned not null,
     unique key (NomeCampionato, NrGio, NrGioReale)

);

create table Partita(
     ID int primary key auto_increment,
     IDgiorn int not null references Giornata(ID) on update cascade on delete no action,
     NrPart tinyint not null,
     unique key(IDgiorn, NrPart),
     FantasquadraCasa varchar(20) not null references Fantasquadra(nome) on update cascade on delete no action,
     FantasquadraOspite varchar(20) not null references Fantasquadra(nome) on update cascade on delete no action,
     PunteggioCasa numeric(3,1) default 0,
     PunteggioOspite numeric(3,1) default 0,
     GolCasa tinyint default null,
     GolOspite tinyint default null
);

create table Classifica(
     NomeCampionato varchar(20) not null references Campionato(Nome) on update cascade on delete no action,
     NomeSq varchar(20) not null references Fantasquadra(nome) on update cascade on delete no action,
     Vinte tinyint default 0,
     Pareggiate tinyint default 0,
     Perse tinyint default 0,
     Punti tinyint default 0,
     GolF tinyint default 0,
     GolS tinyint default 0,
     SommaPunteggi numeric(4,1) default 0,
     primary key(NomeCampionato, NomeSq)
);
