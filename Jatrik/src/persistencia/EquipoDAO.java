package persistencia;

import java.util.*;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import tipos.DataListaEquipo;
import dominio.Campeonato;
import dominio.Equipo;
import dominio.Estadio;
import dominio.Jugador;
import dominio.Partido;
import dominio.Usuario;

@Local
public interface EquipoDAO
{
	
	public Equipo insertarEquipo(Equipo e);
	
	public Boolean actualizarEquipo(String equipo, String pais, String localidad,
									Estadio estadio, Usuario u, Collection<Jugador> jugadores,
									Collection<Partido> partidos, Collection<Campeonato> campeonatos);
	
	public void borrarEquipo(Equipo e);
	
	public Equipo encontrarEquipo(String equipo);
	
	public JSONArray obtenerTodosEquipos();
	
	public Boolean existeEquipo(String equipo);
	
	public JSONArray obtenerPaises();

	public JSONObject obtenerLugarEquipo(String nomEquipo);
	
	public Object[] getTaticaEquipo(String nombreEquipo); 
	
	public Boolean modificarTactica(String equipo, Integer ataque, Integer mediocampo, Integer defensa);

	public DataListaEquipo equiposData(String nomEquipo);

	public void restablecerEquipoLuegoPartido(String nomEquipo); // Pone tarjetas amarillas de los jugadores en 0
	
	public Boolean realizarOfertaJugador(String nomUsuario, Integer idJugador, Integer precio, String comentario);
		
}
