package negocio;

import java.util.Date;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONObject;

import tipos.DataListaCampeonato;

@Local
public interface ICampeonatoController 
{
	public JSONObject crearCampeonato(String nomCampeonato, Date inicioCampeonato, Integer cantidadEquipos);
	
	public DataListaCampeonato campeonatosDisponibles();

	public Integer anotarseACampeonato(String nomCampeonato, String nomUsuario);

	public DataListaCampeonato campeonatosEnEjecucion();

	public DataListaCampeonato listarCampEnEjecucionYFinalizados();
	
}
