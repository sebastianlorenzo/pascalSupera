package persistencia;

import java.util.*;

import javax.ejb.*;
import javax.persistence.*;

import org.codehaus.jettison.json.*;

import tipos.DataEquipo;
import tipos.DataJugador;
import tipos.DataListaEquipo;
import dominio.Campeonato;
import dominio.Equipo;
import dominio.Estadio;
import dominio.Jugador;
import dominio.Pais;
import dominio.Partido;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EquipoDAOImpl implements EquipoDAO
{
	
	static final String CONST_TITULAR = "titular";
	
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
	public JSONArray obtenerTodosEquipos() 
	{
		Query query = em.createQuery("SELECT eq FROM Equipo eq");
		List<Equipo> equiposList = query.getResultList();
		JSONArray jequipos = new JSONArray();
		
		for (Equipo e : equiposList) 
		{
			JSONObject ob = new JSONObject();
			try 
			{
				ob.put("equipo", e.getEquipo());
				ob.put("pais", e.getPais());
				ob.put("localidad", e.getLocalidad());
			} 
			catch (JSONException ex) 
			{
				ex.printStackTrace();
			}
			jequipos.put(ob);
		}
		return jequipos;
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

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONObject obtenerLugarEquipo(String nomEquipo) 
	{
		JSONObject paisYLocalidad = new JSONObject();
		Equipo eq = em.find(Equipo.class, nomEquipo);
		
		try
		{
			paisYLocalidad.put("pais", eq.getPais() );
			paisYLocalidad.put("localidad", eq.getLocalidad());
        }
        catch(Exception ex)
		{
            ex.printStackTrace();
        }
		return paisYLocalidad;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Object[]  getTaticaEquipo(String nombreEquipo) 
	{
		Query query = em.createQuery("SELECT e.tacticaDefensa , e.tacticaMediocampo , e.tacticaAtaque "
									+ "FROM  Equipo e "
									+ "WHERE e.equipo = '"+ nombreEquipo + "'");
		List <Object[]> tactica = query.getResultList();
		return tactica.get(0);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean modificarTactica(String equipo, Integer ataque, Integer mediocampo, Integer defensa) 
	{
		Equipo e = em.find(Equipo.class, equipo);
		if (e != null)
		{
			e.setTacticaAtaque(ataque);
			e.setTacticaMediocampo(mediocampo);
			e.setTacticaDefensa(defensa);
			em.merge(e);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DataListaEquipo equiposData() 
	{
		Query query = em.createQuery("SELECT eq FROM Equipo eq ORDER BY eq.puntaje desc");
		List<Equipo> lequipos = query.getResultList();		
		DataListaEquipo dlequipos = new DataListaEquipo();
		
		for (Equipo e: lequipos)
		{
			String nomEq = e.getEquipo();
			DataEquipo de = new DataEquipo(nomEq);
			Collection<Jugador> ljugadores = e.getJugadores();
			
			for(Jugador jug: ljugadores)
			{
				DataJugador dj = new DataJugador(jug.getJugador(), jug.getPosicion(), 
						jug.getVelocidad(), jug.getTecnica(), jug.getAtaque(),
						jug.getDefensa(), jug.getPorteria(), jug.getEstado_jugador());
				de.addDataJugador(dj);
			}
			dlequipos.addDataEquipo(de);
		}
		return dlequipos;
	}
	
}
