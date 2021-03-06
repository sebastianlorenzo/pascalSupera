package negocio;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import tipos.DataListaEquipo;
import tipos.DataListaPartido;
import tipos.DataListaPosicion;
import tipos.DataListaOferta;
import dominio.Equipo;

@Local
public interface IEquipoController 
{
	
	public Boolean existeEquipoRegistrado(String equipo);
	
	public Equipo crearEquipo(String equipo, String pais, String estadio);
	
	public Boolean existeEstadioRegistrado(String estadio);

	public JSONArray obtenerEquipos();

	public JSONObject obtenerZonaEquipo(String nomEquipo);
	
	public JSONObject obtenerTactica(String equipo);
	
	public void modificarTactica(String equipo, Integer ataque, Integer mediocampo, Integer defensa);
	
	public String obtenerJugadoresTitulares(String nomEquipo) throws JSONException;
	
	public String obtenerJugadoresSuplentes(String nomEquipo) throws JSONException;
	
	public Boolean modificarJugadoresTitulares (String nomEquipo, DataListaPosicion titulares) throws JSONException;
		
	public DataListaEquipo obtenerEquiposData(String nombreEq, boolean incluir_equipo);

	public JSONObject realizarOferta(String nomUsuario, Integer idJugador, Integer precio, String comentario);

	public DataListaOferta obtenerOfertasData(String nomUsuario);
	
	public JSONObject aceptarOferta(String nomUsuario, String comentario, Integer idOferta);

	public JSONObject rechazarOferta(String nomUsuario, String comentario, Integer idOferta);

	public DataListaOferta obtenerOfertasRealizadas(String nomUsuario);
	
	public JSONObject obtenerEntrenamiento(String equipo);
	
	public void modificarEntrenamiento(String equipo, Integer ofensivo, Integer defensivo, Integer fisico, Integer porteria);
	
	public void ejecutarEntrenamiento();

	public DataListaPartido obtenerUltimosResultados(String nomUsuario);
	
	public void obtenerJugadoresExternos();
			
}
