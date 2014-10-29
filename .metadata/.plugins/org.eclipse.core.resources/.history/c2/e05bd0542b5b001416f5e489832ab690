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
				Partido partido_nuevo = new Partido(nomCampeonato+"_partido_"+cant, null, null, fecha, null, null, null, null);
				fecha = sumarDiasFecha(fecha, 7);
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
	public JSONArray obtenerCampeonatos()
	{
		return this.campeonatoDAO.obtenerCampeonatos();
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean anotarseACampeonato(String nomCampeonato, String nomUsuario) 
	{
		return this.campeonatoDAO.anotarseACampeonato(nomCampeonato, nomUsuario);
	}

}
