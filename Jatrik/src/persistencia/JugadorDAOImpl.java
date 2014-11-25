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
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean insertarJugador(Jugador j) 
	{
		try
		{
			em.persist(j);
			return true;
		}
		catch (Throwable ex)
		{
			System.out.println("EXCEPCIÓN: " + ex.getClass());
            return false;
		}
	}
	
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
			System.out.print("***** Cambiando el estado del jugador " + j.getJugador() + " de " + j.getEstado_jugador());
			j.setEstado_jugador(estado_jugador);
			em.merge(j);
			System.out.println(" a " + estado_jugador + " (ahora es " +  j.getEstado_jugador() + ").");
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
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String getNombreJugador(Integer idJugador)
	{
		Jugador j = em.find(Jugador.class, idJugador);
		if (j != null)
		{
			return j.getJugador();
		}
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Integer getRegateJugador(Integer idJugador)
	{
		Jugador j = em.find(Jugador.class, idJugador);
		if (j != null)
		{
			return (int) (j.getTecnica() + j.getVelocidad());
		}
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void aumentarHabilidades(Integer idJugador, Float defensa, Float tecnica, Float velocidad, Float ataque, Float porteria){
		Jugador j = em.find(Jugador.class, idJugador);
		if (j != null)
		{
			if (j.getDefensa() < 100)
				j.setDefensa(j.getDefensa() + defensa);
			if (j.getTecnica() < 100)
				j.setTecnica(j.getTecnica() + tecnica);
			if (j.getVelocidad() < 100)
				j.setVelocidad(j.getVelocidad() + velocidad);
			if (j.getAtaque() < 100)
				j.setAtaque(j.getAtaque() + ataque);
			if (j.getPorteria() < 100)
				j.setPorteria(j.getPorteria() + porteria);
			em.merge(j);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sumarGolHistorialJugador(Integer idJugador)
	{
		Jugador j = em.find(Jugador.class, idJugador);
		if (j != null)
		{
			j.setHistoricoGoles(j.getHistoricoGoles() + 1);
			em.merge(j);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sumarTarjetaAmarillaHistorialJugador(Integer idJugador)
	{
		Jugador j = em.find(Jugador.class, idJugador);
		if (j != null)
		{
			j.setHistoricoTarjetasAmarillas(j.getHistoricoTarjetasAmarillas() + 1);
			em.merge(j);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sumarTarjetaRojaHistorialJugador(Integer idJugador)
	{
		Jugador j = em.find(Jugador.class, idJugador);
		if (j != null)
		{
			j.setHistoricoTarjetasRojas(j.getHistoricoTarjetasRojas() + 1);
			em.merge(j);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sumarLesionHistorialJugador(Integer idJugador)
	{
		Jugador j = em.find(Jugador.class, idJugador);
		if (j != null)
		{
			j.setHistoricoLesiones(j.getHistoricoLesiones() + 1);
			em.merge(j);
		}
	}
	
}
