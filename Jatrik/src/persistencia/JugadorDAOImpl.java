package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dominio.Jugador;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class JugadorDAOImpl implements JugadorDAO{
	
	@PersistenceContext(unitName="Jatrik")
	private javax.persistence.EntityManager em;
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Jugador> obtenerJugadoresSinEquipo(){
		/*Query query = em.createQuery("SELECT j FROM Jugador j WHERE j.equipo_equipo is null");
		List<Jugador> resultList = query.getResultList();
		return resultList;*/
		return null;
	}

}
