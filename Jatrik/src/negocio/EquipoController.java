package negocio;

import javax.ejb.*;

import dominio.Equipo;
import dominio.Estadio;
import persistencia.EquipoDAO;
import persistencia.EquipoDAOImpl;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EquipoController implements IEquipoController{
	
	@EJB
	private EquipoDAO equipoDAO;
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean existeEquipoRegistrado(String equipo) {
		return this.equipoDAO.existeEquipo(equipo);
	}
	
	public Equipo crearEquipo(String equipo, String pais, String localidad){
		
		Equipo e = new Equipo(equipo, pais, localidad);
		Estadio estadio = new Estadio("Estadio "+equipo, 10000);
		//e.setEstadio(estadio);
		//estadio.setEquipo(e);
		this.equipoDAO.insertarEquipo(e);
		return e;
	}
}
