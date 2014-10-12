package persistencia;

import java.util.List;

import javax.ejb.Local;

import dominio.Estadio;

@Local
public interface EstadioDAO {

	public Estadio insert(Estadio entity);
	
	public void update(Estadio entity);
	
	public void delete(Estadio entity);
	
	public void delete(Integer id);
	
	public Estadio findById(Integer id);
	
	public List<Estadio> findAll();

}
