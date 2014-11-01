package negocio;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;

import tipos.DataCambio;
import tipos.DataResumenPartido;

@Local
public interface IPartidoController 
{

	public void configurarCambiosPartido(String partido, DataCambio[] cambios);
	
	public DataResumenPartido simularPartido(String partido);

	public JSONArray obtenerPartidosPorZona(String nomCampeonato);
	
}
