package persistencia;

import java.util.List;

import dominio.Campeonato;
import dominio.Partido;

public interface PartidoDAO 
{
	
	public Partido insertarPartido(Partido p);
	
	public Partido getPartido(String partido);
	
	public void borrarPartido(Partido p);
	
	public List<Partido> obtenerPartidos();
	
	public void setearCampeonato(String partido, Campeonato campeonato);
	
	
}
