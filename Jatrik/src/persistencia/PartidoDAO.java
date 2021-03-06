package persistencia;

import java.util.Date;
import java.util.List;

import tipos.DataListaPartido;
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

	public DataListaPartido obtenerPartidosLugar(String nomCampeonato);
	
	public void guardarResultadoPartido(int[] tarjetasAmarillas, int[] tarjetasRojas, int[] goles, int[] lesiones, Partido partido, List<Comentario> comentarios);

	public DataListaPartido listarJugados(String nomCampeonato);

	public List<Partido> getPartidosSimular(Date fecha);

	public DataListaPartido obtenerMisPartidos(String nomEquipo);
	
}
