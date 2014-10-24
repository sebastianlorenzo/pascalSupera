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

import tipos.DataCampeonato;
import tipos.DataListaCampeonato;
import dominio.Campeonato;
import dominio.Equipo;
import dominio.Partido;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CampeonatoDAOImpl implements CampeonatoDAO
{
	static final int DIAS_APLAZO = 15;
	
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
			System.out.println("EXCEPCI�N: " + ex.getClass());
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
	
	
	//funcion auxiliar. Suma los d�as recibidos a la fecha  	
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
	    SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    String fecha_hoy = formateador.format(ahora);
	    
		for (Campeonato c : campeonatosList) 
		{	
			String fecha_campeonato = c.getInicioCampeonato().toString();
			
			//aplazar campeonato
			if ((fecha_campeonato.compareTo(fecha_hoy) < 0)  &&
					(c.getEquipos() == null || c.getEquipos().size() < c.getCantEquipos()))
			{
				Date dateIni = sumarDiasFecha(c.getInicioCampeonato(), DIAS_APLAZO);
				c.setInicioCampeonato(dateIni);
				em.merge(c);
				Collection<Partido> partidos = c.getPartidos();
				Iterator<Partido> iterador = partidos.iterator();
				while(iterador.hasNext()){
					Partido p = iterador.next();
					Date fechaPartido = sumarDiasFecha(p.getFechaPartido(), DIAS_APLAZO);
					p.setFechaPartido(fechaPartido);
					em.merge(p);
				}				
			}
			
			fecha_campeonato = c.getInicioCampeonato().toString();
			
			if ((fecha_campeonato.compareTo(fecha_hoy) >=0) &&
					(c.getEquipos() == null || c.getEquipos().size() < c.getCantEquipos()))
			{
				DataCampeonato dca = new DataCampeonato();
				String nomCampeonato = c.getCampeonato();
				dca.setNomCampeonato(nomCampeonato);
				Date fechaCamp = c.getInicioCampeonato();
				formateador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
			
			if(listEquipos.size() == c.getCantEquipos()){
				Collection<Partido> listPartidos = c.getPartidos();
				for(Equipo eq : listEquipos){
					Iterator<Partido> iter = listPartidos.iterator();
					
					int cant = 0;
					while(iter.hasNext())
					{
						Partido p = iter.next();
						if ((cant < (listEquipos.size()-1)) && (p.getEquipoLocal() == null)){
							p.setEquipoLocal(eq);
							p.setEstadio(eq.getEstadio());	
							em.merge(p);
							cant++;
						}
					}
				}
				Iterator<Partido> iter2 = listPartidos.iterator();
				for(Equipo eq2 : listEquipos){
					
					Iterator<Equipo> iter3 = listEquipos.iterator();
					int cant = 0;
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
	    SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    String fecha_hoy = formateador.format(ahora);
	    
		for (Campeonato c : campEnEjecucion) 
		{	
			String fecha_campeonato = c.getInicioCampeonato().toString();
			String nomCamp = c.getCampeonato();
			Integer cantidadEquipos = c.getCantEquipos();			
			Integer numero_partido = cantidadEquipos*(cantidadEquipos-1);
			
			Partido par = em.find(Partido.class, nomCamp+"_partido_"+numero_partido);
			
			String fecha_ultimo_partido = par.getFechaPartido().toString();
			if ((fecha_campeonato.compareTo(fecha_hoy) < 0) && (fecha_ultimo_partido.compareTo(fecha_hoy) >=0))
			{
				DataCampeonato dca = new DataCampeonato();
				String nomCampeonato = c.getCampeonato();
				dca.setNomCampeonato(nomCampeonato);
				Date fechaCamp = c.getInicioCampeonato();
				formateador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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

}
