package persistencia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.*;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import dominio.Campeonato;
import dominio.Equipo;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CampeonatoDAOImpl implements CampeonatoDAO
{
	
	@PersistenceContext(unitName="Jatrik")
	private javax.persistence.EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Campeonato insertarCampeonato(Campeonato c) 
	{
		try
		{
			em.persist(c);
			return c;
		}
		catch (Throwable ex)
		{
			System.out.println("EXCEPCIÓN: " + ex.getClass());
            return null;
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void borrarCampeonato(Campeonato c)
	{
		em.remove(c);	
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Campeonato buscarCampeonato(String campeonato) 
	{
		Query query = em.createQuery("SELECT c FROM Campeonato c"
				   + "WHERE c.campeonato = :campeonato");
		query.setParameter("campeonato", campeonato);
		
		ArrayList<Campeonato> lst = (ArrayList<Campeonato>) query.getResultList();
		if (lst.isEmpty())
		{
		return null;
		}
		else
		{
		return lst.get(0);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean existeCampeonato(String campeonato)
	{
		return (em.find(Campeonato.class, campeonato) != null);
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONArray obtenerCampeonatos()
	{
		Query query = em.createQuery("SELECT c FROM Campeonato c");
		List<Campeonato> campeonatosList = query.getResultList();		
		JSONArray jcampeonatos = new JSONArray();
		
		Date ahora = new Date();
	    SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    String fecha_hoy = formateador.format(ahora);
	    
		for (Campeonato c : campeonatosList) 
		{	
			String fecha_campeonato = c.getInicioCampeonato().toString();
			if ((fecha_campeonato.compareTo(fecha_hoy) >=0) &&
					(c.getEquipos() == null || c.getEquipos().size() < c.getCantEquipos()))
			{
				JSONObject ob = new JSONObject();
				try 
				{
					ob.put("campeonato", c.getCampeonato());
				} 
				catch (JSONException e) 
				{
					e.printStackTrace();
				}
				jcampeonatos.put(ob);
			}
		}		
		return jcampeonatos;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean anotarseACampeonato(String nomCampeonato, String nomUsuario) 
	{	
		Campeonato c = em.find(Campeonato.class, nomCampeonato);
		Usuario u = em.find(Usuario.class, nomUsuario);
		Equipo e = u.getEquipo();
		Collection<Equipo> listEquipos = c.getEquipos();
		if(listEquipos.size() < c.getCantEquipos())
		{
			for (Equipo eq: listEquipos)
			{
				if(eq.getEquipo() == e.getEquipo())
					return false;
			}
			listEquipos.add(e);
			Collection<Campeonato> listCampeonatos = e.getCampeonatos();
			listCampeonatos.add(c);
			em.merge(e);
			em.merge(c);		
			return true;
		}
		else 
			return false;
	}

}
