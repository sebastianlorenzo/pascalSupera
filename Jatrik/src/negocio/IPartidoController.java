package negocio;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;

import dominio.Partido;
import tipos.DataCambio;
import tipos.DataListaPartido;

@Local
public interface IPartidoController 
{

	public void configurarCambiosPartido(String partido, DataCambio[] cambios);
	
	public void simularPartido(String partido);

	public JSONArray obtenerPartidosPorZona(String nomCampeonato);

	public DataListaPartido listarPartidosJugados(String nomCampeonato);
	
	public List<Partido> listaPartidosSimular(Date fecha);

	public JSONArray obtenerMisPartidosPorJugar(String nomEquipo);
		
}
