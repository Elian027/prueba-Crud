create database prueba;
use prueba;

create table estudiantes (
CodEst char(4) primary key not null,
NomEst varchar(40) not null,
CelEst char(10) not null,
GenEst varchar(10) not null,
EmailEst varchar(30) not null,
EdadEst char(2) not null);

drop table estudiantes;

select * from estudiantes;