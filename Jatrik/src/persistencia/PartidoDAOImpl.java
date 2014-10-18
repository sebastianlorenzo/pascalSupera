package persistencia;

import javax.ejb.*;
import javax.persistence.PersistenceContext;
import dominio.Partido;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PartidoDAOImpl implements PartidoDAO
{
	
	@PersistenceContext(unitName="Jatrik")
	private javax.persistence.EntityManager em;
	

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Partido getPartido(String partido)
	{
		Partido p = em.find(Partido.class, partido);
		return p;
	}


	@Override
	public Partido insertarPartido(Partido p) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void borrarPartido(Partido p) {
		// TODO Auto-generated method stub
		
	}
	
}
