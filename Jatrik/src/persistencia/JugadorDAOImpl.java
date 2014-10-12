package persistencia;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.PersistenceContext;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class JugadorDAOImpl implements JugadorDAO{
	
	@PersistenceContext(unitName="Jatrik")
	private javax.persistence.EntityManager em;

}
