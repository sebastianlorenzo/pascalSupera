package negocio;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import dominio.Partido;
import tipos.DataCambio;
import tipos.DataListaPartido;

@Local
public interface IPartidoController 
{

	public void configurarCambiosPartido(String partido, DataCambio[] cambios);
	
	public void simularPartido(String partido);

	public DataListaPartido obtenerPartidosPorZona(String nomCampeonato);

	public DataListaPartido listarPartidosJugados(String nomCampeonato);
	
	public List<Partido> listaPartidosSimular(Date fecha);

	public DataListaPartido obtenerMisPartidosPorJugar(String nomEquipo);
		
}
