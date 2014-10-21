package persistencia;

import java.util.List;

import javax.ejb.Local;

import dominio.Equipo;
import dominio.Estadio;

@Local
public interface EstadioDAO
{

	public Estadio insertarEstadio(Estadio estadio);
	
	public void update(Estadio estadio);
	
	public void delete(Estadio estadio);
	
	public void delete(Integer id);
	
	public Estadio findById(Integer id);
	
	public List<Estadio> findAll();
	
	public Boolean existeEstadio(String estadio);

	public void setearEquipo(String nomEstadio, Equipo e);

}
