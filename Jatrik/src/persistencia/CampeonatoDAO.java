package persistencia;

import dominio.Campeonato;

public interface CampeonatoDAO 
{
	public Campeonato insertarCampeonato(Campeonato c);
	
	public void borrarCampeonato(Campeonato c);
	
	public Boolean existeCampeonato(String campeonato);
	
	public Campeonato buscarCampeonato(String campeonato);

}
