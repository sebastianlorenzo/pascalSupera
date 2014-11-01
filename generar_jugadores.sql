INSERT INTO jugador(idjugador, ataque, cant_tarjetas_amarillas, defensa, estado_jugador, jugador, porteria, posicion, posicion_ideal, tecnica, velocidad, equipo_equipo)
SELECT x.id, trunc(random() * 50 + 0), 0, trunc(random() * 50 + 0), 'suplente', 'Nombre'|| x.id , trunc(random() * 50 + 50), 'portero', 'portero', trunc(random() * 50 + 0), trunc(random() * 50 + 0), null FROM generate_series(1,500) AS x(id);

INSERT INTO jugador(idjugador, ataque, cant_tarjetas_amarillas, defensa, estado_jugador, jugador, porteria, posicion, posicion_ideal, tecnica, velocidad, equipo_equipo)
SELECT x.id, trunc(random() * 50 + 0), 0, trunc(random() * 50 + 50), 'suplente', 'Nombre'|| x.id , trunc(random() * 50 + 0), 'defensa', 'defensa', trunc(random() * 50 + 0), trunc(random() * 50 + 50), null FROM generate_series(501,1000) AS x(id);

INSERT INTO jugador(idjugador, ataque, cant_tarjetas_amarillas, defensa, estado_jugador, jugador, porteria, posicion, posicion_ideal, tecnica, velocidad, equipo_equipo)
SELECT x.id, trunc(random() * 50 + 0), 0, trunc(random() * 50 + 50), 'suplente', 'Nombre'|| x.id , trunc(random() * 50 + 0), 'mediocampista', 'mediocampista', trunc(random() * 50 + 50), trunc(random() * 50 + 50), null FROM generate_series(1001,1500) AS x(id);

INSERT INTO jugador(idjugador, ataque, cant_tarjetas_amarillas, defensa, estado_jugador, jugador, porteria, posicion, posicion_ideal, tecnica, velocidad, equipo_equipo)
SELECT x.id, trunc(random() * 50 + 0), 0, trunc(random() * 50 + 0), 'suplente', 'Nombre'|| x.id , trunc(random() * 50 + 0), 'delantero', 'delantero', trunc(random() * 50 + 50), trunc(random() * 50 + 50), null FROM generate_series(1501,2000) AS x(id);
