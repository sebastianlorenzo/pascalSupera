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
	public Estadio insert(Estadio e) {
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

	@Override
	public void update(Estadio entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Estadio entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Estadio findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Estadio> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
