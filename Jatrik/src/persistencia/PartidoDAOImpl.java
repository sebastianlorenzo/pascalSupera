package persistencia;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.*;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import tipos.DataListaPartido;
import tipos.DataPartido;
import dominio.Cambio;
import dominio.Campeonato;
import dominio.Comentario;
import dominio.Equipo;
import dominio.Partido;
import dominio.PartidoResultado;

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
	public void insertarPartidoResultado(PartidoResultado pres, Comentario com) 
	{
		em.persist(com);
		em.persist(pres);				
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
		em.createQuery("DELETE FROM Cambio c WHERE c.partido = '" + p.getPartido() +"'").executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Cambio> getCambiosPartido(String partido, boolean local)
	{
		String select = "p.cambiosLocal";
		if (!local)
		{
			select = "p.cambiosVisitante";
		}
		Query query = em.createQuery("SELECT " + select + " FROM Partido p WHERE p.partido = '" + partido + "'");
		List<Cambio> resultListP = query.getResultList();
		return resultListP;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONArray obtenerPartidosLugar(String nomCampeonato)
	{
		Campeonato camp = em.find(Campeonato.class, nomCampeonato);
		Collection<Partido> partidosList = camp.getPartidos();
		
		JSONArray jpartidos = new JSONArray();
		//obtener partido, luego estadio, luego equipo para obtener lugar
		for (Partido p : partidosList) 
		{
			Equipo eq = p.getEstadio().getEquipo();
			String eqLocal = p.getEquipoLocal().getEquipo();
			String eqVisitante = p.getEquipoVisitante().getEquipo();
			
			JSONObject ob = new JSONObject();
			try 
			{
				ob.put("partido", eqLocal+" vs. "+eqVisitante);
				ob.put("pais", eq.getPais());
				ob.put("localidad", eq.getLocalidad());
			} 
			catch (JSONException ex) 
			{
				ex.printStackTrace();
			}
			jpartidos.put(ob);
		}
		return jpartidos;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void guardarResultadoPartido(int[] tarjetasAmarillas, int[] tarjetasRojas, int[] goles, int[] lesiones, List<Comentario> comentarios)
	{
		PartidoResultado partidoResultado = new PartidoResultado();
		
		Iterator<Comentario> it = comentarios.iterator();
		while (it.hasNext())
		{
			Comentario c = it.next();
			c.setPartidoResultado(partidoResultado);
			em.persist(c);
		}
		
		partidoResultado.setTarjetasAmarillasLocal(tarjetasAmarillas[0]);
		partidoResultado.setTarjetasAmarillasVisitante(tarjetasAmarillas[1]);
		partidoResultado.setTarjetasRojasLocal(tarjetasRojas[0]);
		partidoResultado.setTarjetasRojasVisitante(tarjetasRojas[1]);
		partidoResultado.setGolesLocal(goles[0]);
		partidoResultado.setGolesVisitante(goles[1]);
		partidoResultado.setLesionesLocal(lesiones[0]);
		partidoResultado.setLesionesVisitante(lesiones[1]);
		partidoResultado.setComentarios(comentarios);
	}


	//Faltaría ver el tema de agregar detalle!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DataListaPartido listarJugados(String nomCampeonato) 
	{	
		Campeonato camp = em.find(Campeonato.class, nomCampeonato);
		Collection<Partido> partidos = camp.getPartidos();
		DataListaPartido dlpartidos = new DataListaPartido();
		/*
		for(Partido p: partidos)
		{
			if(p.getPartidoResultado() != null)
			{
				String nomEqLocal = p.getEquipoLocal().getEquipo();
				String nomEqVisitante = p.getEquipoVisitante().getEquipo();
				String nomPartido = nomEqLocal+" vs. "+nomEqVisitante;
				
				Date fechaPartido = p.getFechaPartido();
				SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy hh:mm");
			    String fecha = formateador.format(fechaPartido);
						
				Integer golesLocal = p.getPartidoResultado().getGolesLocal();
				Integer golesVisitante = p.getPartidoResultado().getGolesVisitante();
				
				DataPartido dp = new DataPartido(nomPartido, nomEqLocal, nomEqVisitante,
						nomCampeonato, fecha, golesLocal, golesVisitante);
				dlpartidos.addDataPartido(dp);	
			}
		}*/
		return dlpartidos;
	}

}
