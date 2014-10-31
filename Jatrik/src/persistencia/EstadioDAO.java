package persistencia;

import javax.ejb.Local;

import dominio.Equipo;
import dominio.Estadio;

@Local
public interface EstadioDAO
{
	public Estadio insertarEstadio(Estadio estadio);
	
	public Boolean existeEstadio(String estadio);

	public void setearEquipo(String nomEstadio, Equipo e);

}
