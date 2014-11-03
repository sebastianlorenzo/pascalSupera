package persistencia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import tipos.DataResumenPartido;
import dominio.Cambio;
import dominio.Campeonato;
import dominio.Comentario;
import dominio.Equipo;
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
	public void guardarResultadoPartido(int[] tarjetasAmarillas, int[] tarjetasRojas, int[] goles, int[] lesiones, Partido partido, List<Comentario> comentarios)
	{
		
		Iterator<Comentario> it = comentarios.iterator();
		while (it.hasNext())
		{
			Comentario c = it.next();
			em.persist(c);
		}
		
		partido.setTarjetasAmarillasLocal(tarjetasAmarillas[0]);
		partido.setTarjetasAmarillasVisitante(tarjetasAmarillas[1]);
		partido.setTarjetasRojasLocal(tarjetasRojas[0]);
		partido.setTarjetasRojasVisitante(tarjetasRojas[1]);
		partido.setGolesLocal(goles[0]);
		partido.setGolesVisitante(goles[1]);
		partido.setLesionesLocal(lesiones[0]);
		partido.setLesionesVisitante(lesiones[1]);
		partido.setComentarios(comentarios);
		em.merge(partido);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DataListaPartido listarJugados(String nomCampeonato) 
	{	
		Campeonato camp = em.find(Campeonato.class, nomCampeonato);
		Collection<Partido> partidos = camp.getPartidos();
		DataListaPartido dlpartidos = new DataListaPartido();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);		
		Date ahora = calendar.getTime();
		   
		for(Partido p: partidos)
		{	
			Date fecha_p = p.getFechaPartido();

			if(fecha_p.before(ahora))
			{

				String nomEqLocal = p.getEquipoLocal().getEquipo();
				String nomEqVisitante = p.getEquipoVisitante().getEquipo();
				String nomPartido = nomEqLocal+" vs. "+nomEqVisitante;
				
				Date fechaPartido = p.getFechaPartido();
				SimpleDateFormat formateador2 = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
			    String fecha = formateador2.format(fechaPartido);
						
				Integer golesLocal = p.getGolesLocal();
				Integer golesVisitante = p.getGolesVisitante();
				
				DataResumenPartido dp = new DataResumenPartido (nomPartido, nomEqLocal, nomEqVisitante, 
						nomCampeonato, fecha, p.getTarjetasAmarillasLocal(), p.getTarjetasAmarillasVisitante(), 
						  p.getTarjetasRojasLocal(), p.getTarjetasRojasVisitante(),
						  golesLocal, golesVisitante,
						  p.getLesionesLocal(), p.getLesionesVisitante());
				
				Collection<Comentario> listComentarios = p.getComentarios();
				
				List<String> comentariosEnvio = new ArrayList<String>();
				for (Comentario com: listComentarios ){
					if(com.getMostrarJugados()){
						String comentario = com.getMinuto()+" "+com.getComentario();
						comentariosEnvio.add(comentario);
					}		
				}
				if(comentariosEnvio != null)
					dp.setDetalle(comentariosEnvio);
				
				dlpartidos.addDataPartido(dp);	
			}
		}
		return dlpartidos;
	}

}
