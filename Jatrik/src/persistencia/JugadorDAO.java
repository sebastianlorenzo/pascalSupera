package persistencia;

import java.util.ArrayList;

import dominio.Equipo;
import dominio.Jugador;

public interface JugadorDAO 
{
	
	public ArrayList<Jugador> obtenerJugadoresSinEquipo();
	
	public void setearEquipo(Integer idJugador, Equipo e);

}
