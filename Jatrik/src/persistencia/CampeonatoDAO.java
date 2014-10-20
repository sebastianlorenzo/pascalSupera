package persistencia;

import org.codehaus.jettison.json.JSONArray;

import dominio.Campeonato;

public interface CampeonatoDAO 
{
	public Campeonato insertarCampeonato(Campeonato c);
	
	public void borrarCampeonato(Campeonato c);
	
	public Boolean existeCampeonato(String campeonato);
	
	public Campeonato buscarCampeonato(String campeonato);
	
	public JSONArray obtenerCampeonatos();

}
