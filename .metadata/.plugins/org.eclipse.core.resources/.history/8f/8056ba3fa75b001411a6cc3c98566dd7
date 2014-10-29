package ws;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import tipos.DataListaCampeonato;

import com.google.gson.Gson;

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
        String dia = datosCamp.getString("dia");
        String mes = datosCamp.getString("mes");
        String anio = datosCamp.getString("anio");
        String dateInString = dia+"/"+mes+"/"+anio;
        Date date = null;
        try {
            date = formatter.parse(dateInString);
     
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Date inicioCampeonato = date;
        String cantidad = datosCamp.getString("cantidadEquipos");
        Integer cantidadEquipos =  Integer.parseInt(cantidad);
        
        return iCampeonatoController.crearCampeonato(nomCampeonato, inicioCampeonato, cantidadEquipos).toString();        
    }
	
	@GET
	@Path("listarCampeonatos")
	@Produces(MediaType.APPLICATION_JSON)
	public String listarCampeonatos()
	{			
		Gson g = new Gson();
		DataListaCampeonato dataCamp = this.iCampeonatoController.campeonatosDisponibles();
		return g.toJson(dataCamp);	
	}
	
	@POST
	@Path("inscribirse")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String anotarseACampeonato(String datos) throws JSONException
	{
		JSONObject datosCampI = new JSONObject(datos);
		String nomCampeonato = datosCampI.getString("nomCampeonato");
		String nomUsuario = datosCampI.getString("nomUsuario");
		Boolean ok = this.iCampeonatoController.anotarseACampeonato(nomCampeonato, nomUsuario);
		JSONObject json = new JSONObject();
		try
		{
			json.put("inscripcion", ok );
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return json.toString();
		
	}

}
