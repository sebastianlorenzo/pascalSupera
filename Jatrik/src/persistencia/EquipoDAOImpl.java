package persistencia;

import java.util.*;
import javax.ejb.*;
import javax.persistence.*;
import org.codehaus.jettison.json.*;
import dominio.*;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EquipoDAOImpl implements EquipoDAO
{
	
	@PersistenceContext(unitName="Jatrik")
	private javax.persistence.EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Equipo insertarEquipo(Equipo e) 
	{
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
	public Boolean actualizarEquipo(String equipo, String pais, String localidad,
									Estadio estadio, Usuario usuario, Collection<Jugador> jugadores,
									Collection<Partido> partidos, Collection<Campeonato> campeonatos) 
	{
		Equipo e = em.find(Equipo.class, equipo);
		if (e != null)
		{
			e.setEquipo(equipo);
			e.setPais(pais);
			e.setLocalidad(localidad);
			e.setEstadio(estadio);
			e.setUsuario(usuario);
			e.setJugadores(jugadores);
			e.setPartidos(partidos);
			e.setCampeonatos(campeonatos);
			em.merge(e);
			return true;
		}
		return false;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void borrarEquipo(Equipo e)
	{
		em.remove(e);	
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Equipo encontrarEquipo(String equipo) 
	{
		return em.find(Equipo.class, equipo);
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Equipo> obtenerTodosEquipos() 
	{
		Query query = em.createQuery("SELECT eq FROM Equipo eq");
		List<Equipo> resultList = query.getResultList();
		return resultList;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean existeEquipo(String equipo)
	{
		return (em.find(Equipo.class, equipo) != null);
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONArray obtenerPaises()
	{
		Query query = em.createQuery("SELECT p FROM Pais p");
		List<Pais> paisesList = query.getResultList();		
		JSONArray jpaises = new JSONArray();
		
		for (Pais p : paisesList) 
		{
			JSONObject ob = new JSONObject();
			try 
			{
				ob.put("pais", p.getPais());
				ob.put("localidad", p.getLocalidad());
			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
			jpaises.put(ob);
		}
		
		return jpaises;
	}
	
}
