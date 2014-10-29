package persistencia;

import java.util.ArrayList;

import dominio.Equipo;
import dominio.Jugador;

public interface JugadorDAO 
{
	
	public ArrayList<Jugador> obtenerJugadoresSinEquipo();

	public ArrayList<Jugador> obtenerPorterosSinEquipo();
	
	public ArrayList<Jugador> obtenerDefensasSinEquipo();
	
	public ArrayList<Jugador> obtenerMediocampistasSinEquipo();
	
	public ArrayList<Jugador> obtenerDelanteroSinEquipo();
	
	public void setearEquipo(Integer idJugador, Equipo e);
	
	public void setearPosicion(Integer idJugador, String posicion);
	
	public void setearEstadoJugador(Integer idJugador, String estadoJugador);

}
