package negocio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import dominio.Campeonato;
import dominio.Partido;
import persistencia.CampeonatoDAO;
import persistencia.CampeonatoDAOImpl;
import persistencia.PartidoDAO;
import persistencia.PartidoDAOImpl;
import tipos.Constantes;
import tipos.DataListaCampeonato;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CampeonatoController implements ICampeonatoController
{
	@EJB
	private CampeonatoDAO campeonatoDAO;
	@EJB
	private PartidoDAO partidoDAO;
	
	public CampeonatoController()
	{
		this.campeonatoDAO = new CampeonatoDAOImpl();
		this.partidoDAO = new PartidoDAOImpl();
	}
	
	//funcion auxiliar. Suma los días recibidos a la fecha  	
	public Date sumarDiasFecha(Date fecha, int dias)
	{
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(fecha);
	    calendar.add(Calendar.DAY_OF_YEAR, dias);
	    return calendar.getTime(); 
	 }
	
	//funcion auxiliar. Sumo numero de horas
	public Date sumarHorasFecha(Date fecha, int horas)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.HOUR, horas);
		return calendar.getTime();
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONObject crearCampeonato(String nomCampeonato, Date inicioCampeonato, Integer cantidadEquipos)
	{	
		JSONObject jsonCampeonato = new JSONObject();
		
		if(this.campeonatoDAO.existeCampeonato(nomCampeonato))
		{	
			try
			{
				jsonCampeonato.put("campeonato", false);
				jsonCampeonato.put("mensaje", "ERROR. Existe campeonato con ese nombre.");
	        }
	        catch(Exception ex)
			{
	            ex.printStackTrace();
	        }
		}
		else
		{
			Campeonato c = new Campeonato(nomCampeonato, inicioCampeonato, cantidadEquipos);
			
			ArrayList<Partido> partidos = new ArrayList<Partido>();
			int cant;
			Date fecha = inicioCampeonato;
			for(cant=1; cant<=cantidadEquipos*(cantidadEquipos-1); cant++){
				fecha = sumarHorasFecha(fecha, Constantes.HORA_PARTIDOS);
				Partido partido_nuevo = new Partido(nomCampeonato+"_partido_"+cant, null, null, fecha, null, null, null, null);
				fecha = sumarDiasFecha(fecha, Constantes.DIAS_ENTRE_PARTIDOS);
				this.partidoDAO.insertarPartido(partido_nuevo);				
				partidos.add(partido_nuevo);
			}
			c.setPartidos(partidos);						
			this.campeonatoDAO.insertarCampeonato(c);
			
			Iterator<Partido> iter = partidos.iterator();
			while(iter.hasNext()){
					Partido p = iter.next();
					String nomPartido = p.getPartido();
					this.partidoDAO.setearCampeonato(nomPartido, c);
			}
			
			try
			{
				jsonCampeonato.put("campeonato", true);
				jsonCampeonato.put("mensaje","Campeonato creado correctamente.");
	        }
	        catch(Exception ex)
			{
	            ex.printStackTrace();
	        }
		}
		return jsonCampeonato;
	}

	//obtengo todos los campeonatos disponibles para inscripción
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DataListaCampeonato campeonatosDisponibles() 
	{
		return this.campeonatoDAO.listarCampeonatosDisponibles();
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Integer anotarseACampeonato(String nomCampeonato, String nomUsuario) 
	{
		//verificar si esta anotado
		Boolean anotado= this.campeonatoDAO.anotadoPreviamente(nomUsuario);
		if (anotado)
			return -1;
		else{
			Boolean ok= this.campeonatoDAO.anotarseACampeonato(nomCampeonato, nomUsuario);
			if(!ok){
				return 0;
			}
			else{
				boolean lleno = this.campeonatoDAO.campeonatoCompleto(nomCampeonato);
				
				if(lleno){
				//verificamos si quedan campeonatos disponibles, en caso contrario se crea uno nuevo
					boolean creoCampeonato = this.campeonatoDAO.hayCampeonatosDisponibles();
					if(creoCampeonato){
											
						Calendar calendar = Calendar.getInstance();
						calendar.set(Calendar.MINUTE, 0);
						calendar.set(Calendar.SECOND, 0);
						calendar.set(Calendar.MILLISECOND, 0);
						Date hoy = calendar.getTime();
						Date inicioCampeonato = sumarDiasFecha(hoy, Constantes.DIAS_APLAZO);
						
						int num=1;
						while(this.campeonatoDAO.existeCampeonato(nomCampeonato+num))
							num++;
						
						JSONObject ob = crearCampeonato(nomCampeonato+num, inicioCampeonato, 2);
						try{
							ob.put("camp",nomCampeonato+num);
						}
				        catch(Exception ex)
						{
				            ex.printStackTrace();
				        }
					}
				}
				return 1;
			}
		}
	}

	//obtengo todos los campeonatos en ejecución
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DataListaCampeonato campeonatosEnEjecucion() 
	{
		return this.campeonatoDAO.listarCampeonatosEnEjecucion();
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONArray listarCampEnEjecucionYFinalizados() 
	{
		return this.campeonatoDAO.listarCampEnEjecucionYFinalizados();
	}

}
