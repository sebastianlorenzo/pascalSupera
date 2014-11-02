package persistencia;

import java.util.List;
import org.codehaus.jettison.json.JSONArray;
import dominio.Cambio;
import dominio.Campeonato;
import dominio.Comentario;
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
	
	public List<Cambio> getCambiosPartido(String partido, boolean local);

	public JSONArray obtenerPartidosLugar(String nomCampeonato);
	
	public void guardarResultadoPartido(int[] tarjetasAmarillas, int[] tarjetasRojas, int[] goles, int[] lesiones, List<Comentario> comentarios);
	
}
