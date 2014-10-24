
    create table public.campeonato_equipo (
        campeonatos_campeonato varchar(255) not null,
        equipos_equipo varchar(255) not null
    );

    create table public.campeonato_partido (
        campeonato_campeonato varchar(255) not null,
        partidos_partido varchar(255) not null
    );

    create table public.equipo_jugador (
        equipo_equipo varchar(255) not null,
        jugadores_idJugador int4 not null
    );

    create table public.equipo_partido (
        equipo_equipo varchar(255) not null,
        partidos_partido varchar(255) not null
    );

    create table public.estadio_partido (
        estadio_estadio varchar(255) not null,
        partidos_partido varchar(255) not null
    );

    create table public.campeonato (
        campeonato varchar(255) not null,
        cantEquipos int4,
        inicioCampeonato timestamp,
        primary key (campeonato)
    );

    create table public.equipo (
        equipo varchar(255) not null,
        localidad varchar(255),
        pais varchar(255),
        primary key (equipo)
    );

    create table public.estadio (
        estadio varchar(255) not null,
        capacidad int4,
        equipo varchar(255),
        primary key (estadio)
    );

    create table public.jugador (
        idJugador int4 not null,
        ataque int4,
        defensa int4,
        estado_jugador varchar(255),
        jugador varchar(255),
        porteria int4,
        posicion varchar(255),
        tecnica int4,
        velocidad int4,
        equipo_equipo varchar(255),
        primary key (idJugador)
    );

    create table public.pais (
        localidad varchar(255) not null,
        pais varchar(255),
        primary key (localidad)
    );

    create table public.partido (
        partido varchar(255) not null,
        fechaPartido timestamp,
        campeonato_campeonato varchar(255),
        equipoLocal_equipo varchar(255),
        equipoVisitante_equipo varchar(255),
        estadio_estadio varchar(255),
        primary key (partido)
    );

    create table public.usuario (
        login varchar(255) not null,
        capital int4,
        conectado boolean,
        es_admin boolean,
        mail varchar(255),
        password varchar(255),
        equipo varchar(255),
        primary key (login)
    );

    alter table public.campeonato_partido 
        add constraint UK_qel622wydwywu34avof4n9wpi  unique (partidos_partido);

    alter table public.equipo_jugador 
        add constraint UK_l82welmroy9q2qx97fud6ivs4  unique (jugadores_idJugador);

    alter table public.equipo_partido 
        add constraint UK_gytbsr37ln8p6mmqbpjyy6em3  unique (partidos_partido);

    alter table public.estadio_partido 
        add constraint UK_6ajiscv5xwex5i7hg445t9lxb  unique (partidos_partido);

    alter table public.campeonato_equipo 
        add constraint FK_k7mcfwfovv12itg9awsyblm8d 
        foreign key (equipos_equipo) 
        references public.equipo;

    alter table public.campeonato_equipo 
        add constraint FK_3851qsbu1o421r2aitupqvyu5 
        foreign key (campeonatos_campeonato) 
        references public.campeonato;

    alter table public.campeonato_partido 
        add constraint FK_qel622wydwywu34avof4n9wpi 
        foreign key (partidos_partido) 
        references public.partido;

    alter table public.campeonato_partido 
        add constraint FK_ewebbgy5ct326wly7j0kqml2i 
        foreign key (campeonato_campeonato) 
        references public.campeonato;

    alter table public.equipo_jugador 
        add constraint FK_l82welmroy9q2qx97fud6ivs4 
        foreign key (jugadores_idJugador) 
        references public.jugador;

    alter table public.equipo_jugador 
        add constraint FK_lq30w5112apaow29nev280yty 
        foreign key (equipo_equipo) 
        references public.equipo;

    alter table public.equipo_partido 
        add constraint FK_gytbsr37ln8p6mmqbpjyy6em3 
        foreign key (partidos_partido) 
        references public.partido;

    alter table public.equipo_partido 
        add constraint FK_5csqqr409ne4vw4fsscfrajtm 
        foreign key (equipo_equipo) 
        references public.equipo;

    alter table public.estadio_partido 
        add constraint FK_6ajiscv5xwex5i7hg445t9lxb 
        foreign key (partidos_partido) 
        references public.partido;

    alter table public.estadio_partido 
        add constraint FK_ix41670e8r6vwpstrojrcwekl 
        foreign key (estadio_estadio) 
        references public.estadio;

    alter table public.estadio 
        add constraint FK_es3pkcufchab44tdwagonlbvc 
        foreign key (equipo) 
        references public.equipo;

    alter table public.jugador 
        add constraint FK_jltddv363vbt9cehyysuepcje 
        foreign key (equipo_equipo) 
        references public.equipo;

    alter table public.partido 
        add constraint FK_11pj8odckk9psgwfjo3npve9a 
        foreign key (campeonato_campeonato) 
        references public.campeonato;

    alter table public.partido 
        add constraint FK_o6chfqysk1nn832lro339xyre 
        foreign key (equipoLocal_equipo) 
        references public.equipo;

    alter table public.partido 
        add constraint FK_pjgfo2h0up4g0gsksgd1pca7a 
        foreign key (equipoVisitante_equipo) 
        references public.equipo;

    alter table public.partido 
        add constraint FK_nf87hcmti5sjxjwxcphvh67wt 
        foreign key (estadio_estadio) 
        references public.estadio;

    alter table public.usuario 
        add constraint FK_k2bt9tx57xvrxrl8gd5af2dbl 
        foreign key (equipo) 
        references public.equipo;

    create sequence public.hibernate_sequence;
