package negocio;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import tipos.DataListaEquipo;
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

	public DataListaEquipo obtenerEquiposData();
		
}
