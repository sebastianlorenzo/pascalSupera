package persistencia;

import java.util.List;

import javax.ejb.*;
import javax.persistence.PersistenceContext;

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
			System.out.println("EXCEPCI�N: " + ex.getClass());
            return null;
		}
	}

	@Override
	public void update(Estadio entity) {
		
	}

	@Override
	public void delete(Estadio entity) {
		
	}

	@Override
	public void delete(Integer id) {
		
	}

	@Override
	public Estadio findById(Integer id) {
		return null;
	}

	@Override
	public List<Estadio> findAll() {
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean existeEstadio(String estadio)
	{
		return (em.find(Estadio.class, estadio) != null);
	}

}
