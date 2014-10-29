package negocio;

import java.util.Date;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import tipos.DataListaCampeonato;

@Local
public interface ICampeonatoController 
{
	public JSONObject crearCampeonato(String nomCampeonato, Date inicioCampeonato, Integer cantidadEquipos);
	
	public JSONArray obtenerCampeonatos();
	
	public DataListaCampeonato campeonatosDisponibles();

	public Boolean anotarseACampeonato(String nomCampeonato, String nomUsuario);
	
}
