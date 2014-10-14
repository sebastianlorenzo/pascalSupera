package negocio;

import javax.ejb.*;

import dominio.Equipo;
import dominio.Estadio;
import persistencia.EquipoDAO;
import persistencia.EquipoDAOImpl;
import persistencia.EstadioDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EquipoController implements IEquipoController{
	
	@EJB
	private EquipoDAO equipoDAO;
	
	@EJB
	private EstadioDAO estadioDAO;
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean existeEquipoRegistrado(String equipo) {
		return this.equipoDAO.existeEquipo(equipo);
	}
	
	public Equipo crearEquipo(String equipo, String pais, String localidad){
		
		Equipo e = new Equipo(equipo, pais, localidad);
		int capacidadAleatoria = (int) (Math.random() + 1000);
		Estadio estadio = new Estadio("Estadio "+equipo, capacidadAleatoria);
		this.estadioDAO.insertarEstadio(estadio);
		e.setEstadio(estadio);
		this.equipoDAO.insertarEquipo(e);
		estadio.setEquipo(e);
		return e;
	}
}
