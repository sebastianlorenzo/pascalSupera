package persistencia;

import org.codehaus.jettison.json.JSONArray;

import tipos.DataListaCampeonato;
import dominio.Campeonato;

public interface CampeonatoDAO 
{
	public Campeonato insertarCampeonato(Campeonato c);
	
	public void borrarCampeonato(Campeonato c);
	
	public Boolean existeCampeonato(String campeonato);
	
	public Campeonato buscarCampeonato(String campeonato);
	
	public DataListaCampeonato listarCampeonatosDisponibles();

	public Boolean anotarseACampeonato(String nomCampeonato, String nomUsuario);

	public DataListaCampeonato listarCampeonatosEnEjecucion();

	public JSONArray listarCampEnEjecucionYFinalizados();

	public boolean hayCampeonatosDisponibles();

	public boolean campeonatoCompleto(String nomCampeonato);
	
}
