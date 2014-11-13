package persistencia;

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

	public DataListaCampeonato listarCampEnEjecucionYFinalizados();

	public boolean hayCampeonatosDisponibles();

	public boolean campeonatoCompleto(String nomCampeonato);

	public Boolean anotadoPreviamente(String nomUsuario);

	public Integer getCantidadEquipos(String nomCampeonato);

	public void premiarGanadores(String nomCampeonato);
	
}
