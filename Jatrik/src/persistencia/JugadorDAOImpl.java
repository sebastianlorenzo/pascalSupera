package persistencia;

import java.util.ArrayList;
import javax.ejb.*;
import javax.persistence.*;
import tipos.Constantes;
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

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ArrayList<Jugador> obtenerPorterosSinEquipo() {
		Query query = em.createQuery("SELECT j FROM Jugador j WHERE j.equipo IS EMPTY AND j.posicion_ideal = '" + Constantes.CONST_PORTERO + "'");
		ArrayList<Jugador> resultList = (ArrayList<Jugador>) query.getResultList();
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ArrayList<Jugador> obtenerDefensasSinEquipo() {
		Query query = em.createQuery("SELECT j FROM Jugador j WHERE j.equipo IS EMPTY AND j.posicion_ideal = '" + Constantes.CONST_DEFENSA + "'");
		ArrayList<Jugador> resultList = (ArrayList<Jugador>) query.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ArrayList<Jugador> obtenerMediocampistasSinEquipo() {
		Query query = em.createQuery("SELECT j FROM Jugador j WHERE j.equipo IS EMPTY AND j.posicion_ideal = '" + Constantes.CONST_MEDIOCAMPISTA + "'");
		ArrayList<Jugador> resultList = (ArrayList<Jugador>) query.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ArrayList<Jugador> obtenerDelanteroSinEquipo() {
		Query query = em.createQuery("SELECT j FROM Jugador j WHERE j.equipo IS EMPTY AND j.posicion_ideal = '" + Constantes.CONST_DELANTERO + "'");
		ArrayList<Jugador> resultList = (ArrayList<Jugador>) query.getResultList();
		return resultList;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Equipo obtenerEquipo(Integer idJugador)
	{
		Jugador j = em.find(Jugador.class, idJugador);
		return j.getEquipo();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setearEquipo(Integer idJugador, Equipo e) {
		Jugador j = em.find(Jugador.class, idJugador);
		if (j != null)
		{
			j.setEquipo(e);
			em.persist(j);
		}
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setearPosicion(Integer idJugador, String posicion) {
		Jugador j = em.find(Jugador.class, idJugador);
		if (j != null)
		{
			j.setPosicion(posicion);
			em.persist(j);
		}
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setearEstadoJugador(Integer idJugador, String estadoJugador) {
		Jugador j = em.find(Jugador.class, idJugador);
		if (j != null)
		{
			j.setEstado_jugador(estadoJugador);
			em.persist(j);
		}
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sumarTarjetaAmarilla(Integer idJugador)
	{
		Jugador j = em.find(Jugador.class, idJugador);
		if (j != null)
		{
			int tarjetas = j.getCant_tarjetas_amarillas();
			j.setCant_tarjetas_amarillas(tarjetas + 1);
			em.persist(j);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int getCantidadTarjetasAmarillas(Integer idJugador)
	{
		Jugador j = em.find(Jugador.class, idJugador);
		if (j != null)
		{
			return j.getCant_tarjetas_amarillas();
		}
		return -1;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cambiarEstadoJugador(Integer idJugador, String estado_jugador)
	{
		Jugador j = em.find(Jugador.class, idJugador);
		if (j != null)
		{
			j.setEstado_jugador(estado_jugador);
			em.persist(j);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String getPosicionJugador(Integer idJugador)
	{
		Jugador j = em.find(Jugador.class, idJugador);
		if (j != null)
		{
			return j.getPosicion();
		}
		return null;
	}
	
	public String getNombreJugador(Integer idJugador)
	{
		Jugador j = em.find(Jugador.class, idJugador);
		if (j != null)
		{
			return j.getJugador();
		}
		return null;
	}
	
}
