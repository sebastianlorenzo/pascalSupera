package ws;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import negocio.ICampeonatoController;

@ManagedBean
@ApplicationScoped
@Path("/campeonatos")
public class CampeonatoWS 
{	
	@EJB
	private ICampeonatoController iCampeonatoController;

	@POST
	@Path("crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String crearCampeonato(String datos) throws JSONException
	{
		JSONObject datosCamp = new JSONObject(datos);
		String nomCampeonato = datosCamp.getString("nomCampeonato");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateInString = datosCamp.getString("inicioCampeonato");
		Date date = null;
		try {
			date = formatter.parse(dateInString);
System.out.println(date);
System.out.println(formatter.format(date));
	 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Date inicioCampeonato = date;
		String cantidad = datosCamp.getString("cantidadEquipos");
		Integer cantidadEquipos =  Integer.parseInt(cantidad);
		
		return iCampeonatoController.crearCampeonato(nomCampeonato, inicioCampeonato, cantidadEquipos).toString();    	
	}

}
