package persistencia;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

import dominio.Campeonato;
import dominio.Equipo;
import dominio.Estadio;
import dominio.Jugador;
import dominio.Partido;
import dominio.Usuario;

@Local
public interface EquipoDAO {
	
	public Equipo insertarEquipo(Equipo e);
	
	public boolean actualizarEquipo(String equipo, String pais, String localidad,
			Estadio estadio, Usuario u, Collection<Jugador> jugadores,
			Collection<Partido> partidos, Collection<Campeonato> campeonatos);
	
	public void borrarEquipo(Equipo e);
	
	public Equipo encontrarEquipo(String equipo);
	
	public List<Equipo> obtenerTodosEquipos();
	
	public boolean existeEquipo(String equipo);

}
