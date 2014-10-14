package persistencia;

import java.util.List;

import dominio.Jugador;

public interface JugadorDAO {
	
	public List<Jugador> obtenerJugadoresSinEquipo();

}
