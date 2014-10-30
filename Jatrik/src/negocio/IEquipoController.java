package negocio;

import javax.ejb.Local;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import tipos.DataListaEquipo;
import tipos.DataListaPosicion;
import tipos.DataListaOferta;
import dominio.Equipo;

@Local
public interface IEquipoController 
{
	
	public Boolean existeEquipoRegistrado(String equipo);
	
	public Equipo crearEquipo(String equipo, String pais, String localidad, String estadio);

	public JSONArray obtenerPaisesInicial();
	
	public Boolean existeEstadioRegistrado(String estadio);

	public JSONArray obtenerEquipos();

	public JSONObject obtenerZonaEquipo(String nomEquipo);
	
	public JSONObject obtenerTactica(String equipo);
	
	public void modificarTactica(String equipo, Integer ataque, Integer mediocampo, Integer defensa);
	
	public String obtenerJugadoresTitulares(String nomEquipo) throws JSONException;
	
	public String obtenerJugadoresSuplentes(String nomEquipo) throws JSONException;
	
	public Boolean modificarJugadoresTitulares (String nomEquipo, DataListaPosicion titulares) throws JSONException;
		
	public DataListaEquipo obtenerEquiposData(String nombreEq);

	public JSONObject realizarOferta(String nomUsuario, Integer idJugador, Integer precio, String comentario);

	public DataListaOferta obtenerOfertasData(String nomUsuario);
	
}
