package persistencia;

import java.util.List;
import javax.ejb.Local;
import dominio.Equipo;

@Local
public interface EquipoDAO {
	
	public Equipo insertarEquipo(Equipo e);
	
	public void actualizarEquipo(Equipo e);
	
	public void borrarEquipo(Equipo e);
	
	public Equipo encontrarEquipo(String equipo);
	
	public List<Equipo> obtenerTodosEquipos();	
	
	public List<String> obtenerPaises();

}
