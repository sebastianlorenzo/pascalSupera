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

import tipos.Constantes;
import tipos.DataCampeonato;
import tipos.DataListaCampeonato;
import dominio.Campeonato;
import dominio.Equipo;
import dominio.Estadio;
import dominio.Partido;
import dominio.ResultadoCampeonato;
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
	
	
	//funcion auxiliar. Suma los días recibidos a la fecha  	
	public Date sumarDiasFecha(Date fecha, int dias)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, dias);
		return calendar.getTime(); 
	}
		
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DataListaCampeonato listarCampeonatosDisponibles()
	{
		Query query = em.createQuery("SELECT c FROM Campeonato c");
		List<Campeonato> campeonatosList = query.getResultList();		
		DataListaCampeonato dlcampeonatos = new DataListaCampeonato();
		
		Date ahora = new Date();
		
		for (Campeonato c : campeonatosList) 
		{	
			Date fecha_campeonato = c.getInicioCampeonato();
			
			//aplazar campeonato
			if ((fecha_campeonato.before(ahora))  &&
					(c.getEquipos() == null || c.getEquipos().size() < c.getCantEquipos()))
			{
				Date dateIni = sumarDiasFecha(c.getInicioCampeonato(), Constantes.DIAS_APLAZO);
				c.setInicioCampeonato(dateIni);
				em.merge(c);
				Collection<Partido> partidos = c.getPartidos();
				Iterator<Partido> iterador = partidos.iterator();
				while(iterador.hasNext()){
					Partido p = iterador.next();
					Date fechaPartido = sumarDiasFecha(p.getFechaPartido(), Constantes.DIAS_APLAZO);
					p.setFechaPartido(fechaPartido);
					em.merge(p);
				}				
			}
			
			fecha_campeonato = c.getInicioCampeonato();
			
			if ((fecha_campeonato.after(ahora)) &&
					(c.getEquipos() == null || c.getEquipos().size() < c.getCantEquipos()))
			{
				DataCampeonato dca = new DataCampeonato();
				String nomCampeonato = c.getCampeonato();
				dca.setNomCampeonato(nomCampeonato);
				Date fechaCamp = c.getInicioCampeonato();
				SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
			    String fechaInicio = formateador.format(fechaCamp);
				dca.setFechaInicio(fechaInicio);
				Integer bacantes = (c.getCantEquipos() - c.getEquipos().size());
				dca.setDisponibilidad(bacantes);
				Collection<Equipo> lsteq = c.getEquipos();
				
				List<String> lsteqdata = new ArrayList<String>();
				for (Equipo e : lsteq)
				{
					String nomEq = e.getEquipo();
					lsteqdata.add(nomEq);					
				}
				
				dca.setEquiposCampeonato(lsteqdata);
				
				dlcampeonatos.addDataCampeonato(dca);
			}
		}
		
		return dlcampeonatos;		
	}
	
	//funcion auxiliar que permite saber si debemos crear un nuevo campeonato automáticamente.
	@SuppressWarnings("unchecked")
	public boolean hayCampeonatosDisponibles()
	{
		Query query = em.createQuery("SELECT c FROM Campeonato c");
		List<Campeonato> campeonatosList = query.getResultList();
		
		for (Campeonato c : campeonatosList) 
		{
			if (c.getEquipos() == null || c.getEquipos().size() < c.getCantEquipos())
				return false;
		}
		return true;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean anotarseACampeonato(String nomCampeonato, String nomUsuario) 
	{	
		Campeonato c = em.find(Campeonato.class, nomCampeonato);
		Usuario u = em.find(Usuario.class, nomUsuario);
		Equipo e = u.getEquipo();
		Collection<Equipo> listEquipos = c.getEquipos();

		// Si hay lugar en el campeonato
		if(listEquipos.size() < c.getCantEquipos())
		{
			for (Equipo eq: listEquipos)
			{
				if(eq.getEquipo() == e.getEquipo())
					return false;
			}
			e.setPuntaje(0);
			listEquipos.add(e);
			Collection<Campeonato> listCampeonatos = e.getCampeonatos();
			listCampeonatos.add(c);
			em.merge(e);
			em.merge(c);
			
			// El campeonato se llenó
			if(listEquipos.size() == c.getCantEquipos()){
				Collection<Partido> listPartidos = c.getPartidos();
				// Para cada equipo anotado al campeonato
				for(Equipo eq : listEquipos){
					Iterator<Partido> iter = listPartidos.iterator();
					
					int cant = 0;
					// Recorro la lista de partidos y lo seteo como local
					while(iter.hasNext())
					{
						Partido p = iter.next();
						if ((cant < (listEquipos.size()-1)) && (p.getEquipoLocal() == null)){
							p.setEquipoLocal(eq);
							Estadio estadio = eq.getEstadio();
							p.setEstadio(estadio);	
							em.merge(p);
							
							Collection<Partido> partidosEstadio = estadio.getPartidos();
							partidosEstadio.add(p);
							estadio.setPartidos(partidosEstadio);
							cant++;
						}
					}
				}
				Iterator<Partido> iter2 = listPartidos.iterator();
				// Para cada equipo anotado al campeonato
				for(Equipo eq2 : listEquipos){
					
					Iterator<Equipo> iter3 = listEquipos.iterator();
					int cant = 0;
					// Seteo los visitantes
					while(iter3.hasNext() && (cant < (listEquipos.size()-1)))
					{	
						Equipo eqVisitante = iter3.next();
						if (eq2.getEquipo() != eqVisitante.getEquipo()){
							Partido p = iter2.next();
							p.setEquipoVisitante(eqVisitante);
							iter2.hasNext();
							em.merge(p);
							
							cant++;
						}
					}
				}
				
				// Inicializo la tabla de resultados del campeonato
				for(Equipo eq : listEquipos){
					Collection<ResultadoCampeonato> resultadoCampeonato = c.getResultadoCampeonato();
					ResultadoCampeonato r = new ResultadoCampeonato(eq, 0, c);
					resultadoCampeonato.add(r);
					c.setResultadoCampeonato(resultadoCampeonato);
					em.persist(r);
				}
			}
			return true;
		}
		else 
			return false;
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DataListaCampeonato listarCampeonatosEnEjecucion()
	{
		Query query = em.createQuery("SELECT c FROM Campeonato c");
		List<Campeonato> campEnEjecucion = query.getResultList();		
		DataListaCampeonato dlcampeonatos = new DataListaCampeonato();
		Date ahora = new Date();
	    
		for (Campeonato c : campEnEjecucion) 
		{	
			Date fecha_campeonato = c.getInicioCampeonato();
			String nomCamp = c.getCampeonato();
			Integer cantidadEquipos = c.getCantEquipos();			
			Integer numero_partido = cantidadEquipos*(cantidadEquipos-1);
			
			Partido par = em.find(Partido.class, nomCamp+"_partido_"+numero_partido);
			
			Date fecha_ultimo_partido = par.getFechaPartido();
			if ((fecha_campeonato.before(ahora)) && (fecha_ultimo_partido.after(ahora)))
			{
				DataCampeonato dca = new DataCampeonato();
				String nomCampeonato = c.getCampeonato();
				dca.setNomCampeonato(nomCampeonato);
				Date fechaCamp = c.getInicioCampeonato();
				SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
			    String fechaInicio = formateador.format(fechaCamp);
				dca.setFechaInicio(fechaInicio);
				
				Collection<Equipo> lsteq = c.getEquipos();
				
				List<String> lsteqdata = new ArrayList<String>();
				for (Equipo e : lsteq)
				{
					String nomEq = e.getEquipo();
					lsteqdata.add(nomEq);					
				}
				dca.setEquiposCampeonato(lsteqdata);
				
				dlcampeonatos.addDataCampeonato(dca);
			}	
		}		
		return dlcampeonatos;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONArray listarCampEnEjecucionYFinalizados() 
	{	
		Query query = em.createQuery("SELECT c FROM Campeonato c");
		List<Campeonato> campeonatos = query.getResultList();
		JSONArray jcampeonatos = new JSONArray();
		JSONObject obj;

		Date ahora = new Date();
	    
		for (Campeonato c : campeonatos) 
		{	
			Date fecha_campeonato = c.getInicioCampeonato();
			
			if (fecha_campeonato.before(ahora))
			{
				try 
				{	
					String nomCamp = c.getCampeonato();
					obj = new JSONObject();
					obj.put("nomCampeonato", nomCamp);
					jcampeonatos.put(obj);
				} 
				catch (JSONException e) 
				{
					e.printStackTrace();
				}
				
			}
		}
		return jcampeonatos;
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean campeonatoCompleto(String nomCampeonato) 
	{
		Campeonato c = em.find(Campeonato.class, nomCampeonato);
		Query query = em.createQuery("SELECT c.equipos FROM Campeonato c "
				+ "WHERE c.campeonato = '"+ nomCampeonato+"'");
		List<Equipo> listEquipos = query.getResultList();
		if(listEquipos.size() < c.getCantEquipos())
			return false;
		else
			return true;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean anotadoPreviamente(String nomUsuario) 
	{
		Query query = em.createQuery("SELECT u.equipo FROM Usuario u "
				                   + "WHERE u.login = '"+ nomUsuario + "'");
		List<Equipo> equipos = query.getResultList();
		if (equipos != null)
		{
			Integer puntaje = equipos.get(0).getPuntaje();
		
			if (puntaje < 0)
			{
				return false;
			}
		}
		return true;
	}
	

	public Integer getCantidadEquipos(String nomCampeonato)
	{
		Campeonato c = em.find(Campeonato.class, nomCampeonato);
		if (c != null)
		{
			return c.getCantEquipos();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	public void premiarGanadores(String nomCampeonato)
	{
		// Asigno puntos y plata a los tres primeros lugares
		// Asigno 15, 10 y 5 puntos a cada posición
		Query query = em.createQuery("SELECT r FROM ResultadoCampeonato r, Campeonato c "
								   + "WHERE r.campeonato = c AND c.campeonato = '" + nomCampeonato + "' "
								   + "ORDER BY r.puntaje DESC");
		Iterator<ResultadoCampeonato> it = query.getResultList().iterator();
		int cant = 0;
		int puntos = 15;
		while (it.hasNext() && (cant < 3))
		{
			ResultadoCampeonato r = it.next();
			Equipo e              = r.getEquipo();
			Usuario u             = e.getUsuario();
			Integer capital       = u.getCapital();
			
			capital += puntos * (capital * Constantes.CONT_PORCENTAJE_CAPITAL / 100); 
			u.setCapital(capital);
			r.setPuntaje(r.getPuntaje() + puntos);
			
			// Además restablezco el puntaje del equipo a -1. 
	        e.setPuntaje(-1);
	        
			em.merge(r);
			em.merge(u);
			em.merge(e);
			
			cant++;
			puntos -= 5;
		}
	}
	
}
