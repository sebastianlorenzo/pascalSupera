
    create table public.usuario (
        id int4 not null,
        login varchar(100) not null,
        password varchar(255) not null,
        primary key (id)
    );
