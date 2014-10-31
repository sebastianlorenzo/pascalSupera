package persistencia;

import java.util.List;
import dominio.Cambio;
import dominio.Campeonato;
import dominio.Partido;

public interface PartidoDAO 
{
	
	public Partido insertarPartido(Partido p);
	
	public Partido getPartido(String partido);
		
	public void borrarPartido(Partido p);
	
	public List<Partido> obtenerPartidos();
	
	public void setearCampeonato(String partido, Campeonato campeonato);
	
	public void setearCambios(Partido p, List<Cambio> cambios, boolean local);
	
	public void eliminarCambiosHechosDurantePartido(Partido p);
	
}
