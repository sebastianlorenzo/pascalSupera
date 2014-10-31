package persistencia;

import java.util.List;

import javax.ejb.*;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dominio.Cambio;
import dominio.Campeonato;
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
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Partido insertarPartido(Partido p)
	{
		try
		{
			em.persist(p);
			return p;
		}
		catch (Throwable ex)
		{
			System.out.println("EXCEPCIÓN: " + ex.getClass());
            return null;
		}
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void borrarPartido(Partido p)
	{
		em.remove(p);
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Partido> obtenerPartidos()
	{
		Query query = em.createQuery("SELECT p FROM Partido p");
		List<Partido> resultListP = query.getResultList();
		return resultListP;
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setearCampeonato(String partido, Campeonato campeonato)
	{
		
		Partido p = em.find(Partido.class, partido);
		if (p != null)
		{
			p.setCampeonato(campeonato);
			em.merge(p);
		}
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setearCambios(Partido p, List<Cambio> cambios, boolean local)
	{
		if (p != null)
		{
			if (local)
			{
				p.setCambiosLocal(cambios);
			}
			else
			{
				p.setCambiosVisitante(cambios);
			}
			em.persist(p);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminarCambiosHechosDurantePartido(Partido p)
	{
		em.createQuery("DELETE FROM Cambio c WHERE c.partido = '" + p + "'").executeUpdate();
	}
	
}
