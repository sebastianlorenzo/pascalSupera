package negocio;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;

import dominio.Equipo;

@Local
public interface IEquipoController {
	
	public Boolean existeEquipoRegistrado(String equipo);
	
	public Equipo crearEquipo(String equipo, String pais, String localidad, String estadio);

	public JSONArray obtenerPaisesInicial();
}
