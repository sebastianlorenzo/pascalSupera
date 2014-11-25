package persistencia;

import java.util.ArrayList;

import dominio.Equipo;
import dominio.Jugador;

public interface JugadorDAO 
{
	
	public Boolean insertarJugador(Jugador j);
	
	public ArrayList<Jugador> obtenerJugadoresSinEquipo();

	public ArrayList<Jugador> obtenerPorterosSinEquipo();
	
	public ArrayList<Jugador> obtenerDefensasSinEquipo();
	
	public ArrayList<Jugador> obtenerMediocampistasSinEquipo();
	
	public ArrayList<Jugador> obtenerDelanteroSinEquipo();
	
	public Equipo obtenerEquipo(Integer idJugador);
	
	public void setearEquipo(Integer idJugador, Equipo e);
	
	public void setearPosicion(Integer idJugador, String posicion);
	
	public void setearEstadoJugador(Integer idJugador, String estadoJugador);

	public void sumarTarjetaAmarilla(Integer idJugador);
	
	public int getCantidadTarjetasAmarillas(Integer idJugador);
	
	public void cambiarEstadoJugador(Integer idJugador, String estado_jugador);
	
	public String getPosicionJugador(Integer idJugador);
	
	public String getNombreJugador(Integer idJugador);
	
	public Integer getRegateJugador(Integer idJugador);
	
	public void aumentarHabilidades(Integer idJugador, Float defensa, Float tecnica, Float velocidad, Float ataque, Float porteria);

	public void sumarGolHistorialJugador(Integer idJugador);

	public void sumarTarjetaAmarillaHistorialJugador(Integer idJugador);

	public void sumarTarjetaRojaHistorialJugador(Integer idJugador);

	public void sumarLesionHistorialJugador(Integer idJugador);
	
}
