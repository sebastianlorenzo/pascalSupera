
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

    create table public.equipo_ofertas_realizadas (
        equipo_equipo varchar(255) not null,
        ofertasRealizadas_idOferta int4 not null
    );

    create table public.equipo_ofertas_recibidas (
        equipo_equipo varchar(255) not null,
        ofertasRecibidas_idOferta int4 not null
    );

    create table public.equipo_partido (
        equipo_equipo varchar(255) not null,
        partidos_partido varchar(255) not null
    );

    create table public.estadio_partido (
        estadio_estadio varchar(255) not null,
        partidos_partido varchar(255) not null
    );

    create table public.oferta_jugadores (
        jugador_idJugador int4 not null,
        oferta_jugadores_idOferta int4 not null
    );

    create table public.partido_comentario (
        partido_partido varchar(255) not null,
        comentarios_idComentario int4 not null
    );

    create table public.cambio (
        idCambio int4 not null,
        idJugadorEntrante int4,
        idJugadorSaliente int4,
        minutoCambio int4,
        partido_partido varchar(255),
        cambiosVisitante_partido varchar(255),
        cambiosLocal_partido varchar(255),
        primary key (idCambio)
    );

    create table public.campeonato (
        campeonato varchar(255) not null,
        cantEquipos int4,
        inicioCampeonato timestamp,
        primary key (campeonato)
    );

    create table public.comentario (
        idComentario int4 not null,
        comentario varchar(255),
        minuto int4,
        partido_partido varchar(255),
        primary key (idComentario)
    );

    create table public.equipo (
        equipo varchar(255) not null,
        cant_cambios_realizados int4,
        localidad varchar(255),
        pais varchar(255),
        puntaje int4,
        tacticaAtaque int4,
        tacticaDefensa int4,
        tacticaMediocampo int4,
        primary key (equipo)
    );

    create table public.estadio (
        estadio varchar(255) not null,
        altura int4,
        capacidad int4,
        equipo varchar(255),
        primary key (estadio)
    );

    create table public.jugador (
        idJugador int4 not null,
        ataque int4,
        cant_tarjetas_amarillas int4,
        defensa int4,
        estado_jugador varchar(255),
        jugador varchar(255),
        porteria int4,
        posicion varchar(255),
        posicion_ideal varchar(255),
        tecnica int4,
        velocidad int4,
        equipo_equipo varchar(255),
        primary key (idJugador)
    );

    create table public.mensaje (
        idMensaje int8 not null,
        leido boolean,
        texto varchar(255),
        emisorMensaje_login varchar(255),
        receptorMensaje_login varchar(255),
        primary key (idMensaje)
    );

    create table public.notificacion (
        idNotificacion int8 not null,
        texto varchar(500),
        vista boolean,
        receptorNotificacion_login varchar(255),
        primary key (idNotificacion)
    );

    create table public.oferta (
        idOferta int4 not null,
        comentario varchar(255),
        comentario_acepta varchar(255),
        estado_oferta varchar(255),
        fecha_oferta timestamp,
        precio int4,
        equipoDestino_equipo varchar(255),
        equipoOrigen_equipo varchar(255),
        jugadorEnVenta_idJugador int4,
        primary key (idOferta)
    );

    create table public.pais (
        localidad varchar(255) not null,
        pais varchar(255),
        primary key (localidad)
    );

    create table public.partido (
        partido varchar(255) not null,
        fechaPartido timestamp,
        golesLocal int4,
        golesVisitante int4,
        lesionesLocal int4,
        lesionesVisitante int4,
        tarjetasAmarillasLocal int4,
        tarjetasAmarillasVisitante int4,
        tarjetasRojasLocal int4,
        tarjetasRojasVisitante int4,
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

    create table public.usuario_amigos (
        usuario_login varchar(255) not null,
        misAmigosChat_login varchar(255) not null
    );

    create table public.usuario_mensajesEnviados (
        usuario_login varchar(255) not null,
        mensajesEnviados_idMensaje int8 not null
    );

    create table public.usuario_mensajesRecibidos (
        usuario_login varchar(255) not null,
        mensajesRecibidos_idMensaje int8 not null
    );

    create table public.usuario_notificaciones (
        usuario_login varchar(255) not null,
        notificacionesRecibidas_idNotificacion int8 not null
    );

    alter table public.campeonato_partido 
        add constraint UK_qel622wydwywu34avof4n9wpi  unique (partidos_partido);

    alter table public.equipo_jugador 
        add constraint UK_l82welmroy9q2qx97fud6ivs4  unique (jugadores_idJugador);

    alter table public.equipo_ofertas_realizadas 
        add constraint UK_eavouqg5a9fwrr0nw677not49  unique (ofertasRealizadas_idOferta);

    alter table public.equipo_ofertas_recibidas 
        add constraint UK_nc2huhrpd05yohw7dyggn864c  unique (ofertasRecibidas_idOferta);

    alter table public.equipo_partido 
        add constraint UK_gytbsr37ln8p6mmqbpjyy6em3  unique (partidos_partido);

    alter table public.estadio_partido 
        add constraint UK_6ajiscv5xwex5i7hg445t9lxb  unique (partidos_partido);

    alter table public.oferta_jugadores 
        add constraint UK_84ki5xvluuaj6u5sfnc5nwy5q  unique (oferta_jugadores_idOferta);

    alter table public.partido_comentario 
        add constraint UK_a3utqtwk0ue9wurf5yvdcvred  unique (comentarios_idComentario);

    alter table public.usuario_mensajesEnviados 
        add constraint UK_8h29hety033e3pfj8054fwkbr  unique (mensajesEnviados_idMensaje);

    alter table public.usuario_mensajesRecibidos 
        add constraint UK_hrqjp7i4k5ujfksrh4gsbyvak  unique (mensajesRecibidos_idMensaje);

    alter table public.usuario_notificaciones 
        add constraint UK_89iq8heaqmye4v93kmxnkan7f  unique (notificacionesRecibidas_idNotificacion);

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

    alter table public.equipo_ofertas_realizadas 
        add constraint FK_eavouqg5a9fwrr0nw677not49 
        foreign key (ofertasRealizadas_idOferta) 
        references public.oferta;

    alter table public.equipo_ofertas_realizadas 
        add constraint FK_n9gkc654nhb55jrbbhsiqrbnf 
        foreign key (equipo_equipo) 
        references public.equipo;

    alter table public.equipo_ofertas_recibidas 
        add constraint FK_nc2huhrpd05yohw7dyggn864c 
        foreign key (ofertasRecibidas_idOferta) 
        references public.oferta;

    alter table public.equipo_ofertas_recibidas 
        add constraint FK_mvvo8i72tbcna26skg92dfjug 
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

    alter table public.oferta_jugadores 
        add constraint FK_84ki5xvluuaj6u5sfnc5nwy5q 
        foreign key (oferta_jugadores_idOferta) 
        references public.oferta;

    alter table public.oferta_jugadores 
        add constraint FK_bgfkvvjb7ms0qllcj93ry6knj 
        foreign key (jugador_idJugador) 
        references public.jugador;

    alter table public.partido_comentario 
        add constraint FK_a3utqtwk0ue9wurf5yvdcvred 
        foreign key (comentarios_idComentario) 
        references public.comentario;

    alter table public.partido_comentario 
        add constraint FK_nivpt9par78n1u3ui7g39uu3p 
        foreign key (partido_partido) 
        references public.partido;

    alter table public.cambio 
        add constraint FK_db2khxvpib5xmrjhspw6g3ade 
        foreign key (partido_partido) 
        references public.partido;

    alter table public.cambio 
        add constraint FK_oajxhru2xhu31q1476pbpq66m 
        foreign key (cambiosVisitante_partido) 
        references public.partido;

    alter table public.cambio 
        add constraint FK_14owakocop4k2t8f7akhjxnp5 
        foreign key (cambiosLocal_partido) 
        references public.partido;

    alter table public.comentario 
        add constraint FK_h2nl0oqakub946p3ae4967peu 
        foreign key (partido_partido) 
        references public.partido;

    alter table public.estadio 
        add constraint FK_es3pkcufchab44tdwagonlbvc 
        foreign key (equipo) 
        references public.equipo;

    alter table public.jugador 
        add constraint FK_jltddv363vbt9cehyysuepcje 
        foreign key (equipo_equipo) 
        references public.equipo;

    alter table public.mensaje 
        add constraint FK_krtsmml61bh27wbyatxxfv9c0 
        foreign key (emisorMensaje_login) 
        references public.usuario;

    alter table public.mensaje 
        add constraint FK_3j18mdk95u7goe6wnx4k9kfq1 
        foreign key (receptorMensaje_login) 
        references public.usuario;

    alter table public.notificacion 
        add constraint FK_mxmpawgraugjxkqj2t5u04oe8 
        foreign key (receptorNotificacion_login) 
        references public.usuario;

    alter table public.oferta 
        add constraint FK_8d2cj49xk01bjwuw1kne6ocbh 
        foreign key (equipoDestino_equipo) 
        references public.equipo;

    alter table public.oferta 
        add constraint FK_lkgh8luft9i3f19v8oy8h02o5 
        foreign key (equipoOrigen_equipo) 
        references public.equipo;

    alter table public.oferta 
        add constraint FK_m479pwas40jj1wmhw1uipul3w 
        foreign key (jugadorEnVenta_idJugador) 
        references public.jugador;

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

    alter table public.usuario_amigos 
        add constraint FK_h950hjw7aaf98ngiyyjl8ej6h 
        foreign key (misAmigosChat_login) 
        references public.usuario;

    alter table public.usuario_amigos 
        add constraint FK_ldj7x4e2mimni3q1pa5s5i0co 
        foreign key (usuario_login) 
        references public.usuario;

    alter table public.usuario_mensajesEnviados 
        add constraint FK_8h29hety033e3pfj8054fwkbr 
        foreign key (mensajesEnviados_idMensaje) 
        references public.mensaje;

    alter table public.usuario_mensajesEnviados 
        add constraint FK_1kkvwjm0dmxsaenmrl9d9fp9n 
        foreign key (usuario_login) 
        references public.usuario;

    alter table public.usuario_mensajesRecibidos 
        add constraint FK_hrqjp7i4k5ujfksrh4gsbyvak 
        foreign key (mensajesRecibidos_idMensaje) 
        references public.mensaje;

    alter table public.usuario_mensajesRecibidos 
        add constraint FK_r3v7ljoc36f539pmfbikbtxg8 
        foreign key (usuario_login) 
        references public.usuario;

    alter table public.usuario_notificaciones 
        add constraint FK_89iq8heaqmye4v93kmxnkan7f 
        foreign key (notificacionesRecibidas_idNotificacion) 
        references public.notificacion;

    alter table public.usuario_notificaciones 
        add constraint FK_roosx8u65m5nps0pgjpefktlq 
        foreign key (usuario_login) 
        references public.usuario;

    create sequence public.hibernate_sequence;
