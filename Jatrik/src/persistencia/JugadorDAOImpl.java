package persistencia;

import java.util.ArrayList;

import javax.ejb.*;
import javax.persistence.*;

import dominio.Equipo;
import dominio.Jugador;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class JugadorDAOImpl implements JugadorDAO
{
	
	@PersistenceContext(unitName="Jatrik")
	private javax.persistence.EntityManager em;
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ArrayList<Jugador> obtenerJugadoresSinEquipo()
	{
		Query query = em.createQuery("SELECT j FROM Jugador j WHERE j.equipo IS EMPTY");
		ArrayList<Jugador> resultList = (ArrayList<Jugador>) query.getResultList();
		return resultList;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setearEquipo(Integer idJugador, Equipo e) {
		Jugador j = em.find(Jugador.class, idJugador);
		if (j != null)
		{
			j.setEquipo(e);;
			em.merge(j);
		}
		
	}
	
	

}
