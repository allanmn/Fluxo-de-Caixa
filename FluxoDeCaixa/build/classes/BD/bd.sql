create database fluxo;

use fluxo;

create table CategoriasContas (codigo integer primary key, descricao varchar(100) not null, positiva tinyint(1) not null);

create table SubCategorias (codSub integer primary key, codCat integer not null,descricao varchar(100) not null, foreign key (codCat) references CategoriasContas(codigo));

create table FluxoCaixa (
    id integer primary key, 
    codSubCat integer, 
    codCat integer,
    dataOcorrencia Date not null, 
    descricao varchar (100) not null, 
    valor double not null, 
    formaPagto integer not null,
    foreign key (codSubcat) references SubCategorias(codSub),
    foreign key (codCat) references CategoriasContas(codigo)
);



