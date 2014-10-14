package negocio;

import javax.ejb.*;

import dominio.Equipo;
import dominio.Estadio;
import persistencia.EquipoDAO;
import persistencia.EstadioDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EquipoController implements IEquipoController{
	
	static final int MAX_CAPACIDAD = 10000;
	
	@EJB
	private EquipoDAO equipoDAO;
	
	@EJB
	private EstadioDAO estadioDAO;
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean existeEquipoRegistrado(String equipo) {
		return this.equipoDAO.existeEquipo(equipo);
	}
	
	public Equipo crearEquipo(String equipo, String pais, String localidad, String nomestadio){
		
		Equipo e = new Equipo(equipo, pais, localidad);
		int capacidad = MAX_CAPACIDAD;
		Estadio estadio = new Estadio(nomestadio, capacidad);
		this.estadioDAO.insertarEstadio(estadio);
		e.setEstadio(estadio);
		this.equipoDAO.insertarEquipo(e);
		estadio.setEquipo(e);
		return e;
	}
}
