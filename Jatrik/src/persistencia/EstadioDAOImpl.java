package persistencia;

import javax.ejb.*;
import javax.persistence.PersistenceContext;

import dominio.Equipo;
import dominio.Estadio;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EstadioDAOImpl implements EstadioDAO {
	
	@PersistenceContext(unitName="Jatrik")
	private javax.persistence.EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Estadio insertarEstadio(Estadio e) {
		try
		{
			em.persist(e);
			return e;
		}
		catch (Throwable ex)
		{
			System.out.println("EXCEPCIÓN: " + ex.getClass());
            return null;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean existeEstadio(String estadio)
	{
		return (em.find(Estadio.class, estadio) != null);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setearEquipo(String nomEstadio, Equipo e) 
	{
		Estadio estadio = em.find(Estadio.class, nomEstadio);
		if (estadio != null)
		{
			estadio.setEquipo(e);;
			em.merge(estadio);
		}	
	}

}
