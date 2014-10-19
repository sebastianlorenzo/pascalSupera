package persistencia;

import java.util.*;
import javax.ejb.Local;
import org.codehaus.jettison.json.JSONArray;
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
	
	public List<Equipo> obtenerTodosEquipos();
	
	public Boolean existeEquipo(String equipo);
	
	public JSONArray obtenerPaises();
	
	public List<Jugador> getJugadoresDelanterosEquipo(String nombreEquipo);
	
	public List<Jugador> getJugadoresMediocampistasEquipo(String nombreEquipo);
	
	public List<Jugador> getJugadoresDefensasEquipo(String nombreEquipo);

}
