package negocio;

import javax.ejb.Local;

import dominio.Equipo;

@Local
public interface IEquipoController {
	
	public Boolean existeEquipoRegistrado(String equipo);
	
	public Equipo crearEquipo(String equipo, String pais, String localidad);

}
