package ws;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import negocio.IPartidoController;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import com.google.gson.Gson;
import tipos.DataCambio;
import tipos.DataResumenPartido;

@ManagedBean
@ApplicationScoped
@Path("/partidos")
public class PartidoWS
{

	@EJB
	private  IPartidoController iPartidoController;
	
	@POST
	@Path("simularPartido")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public String simularPartido(String datos) throws JSONException
	{
		JSONObject datosPartido = new JSONObject(datos);
		String partido          = datosPartido.getString("partido");
		
		DataResumenPartido resumenPartido = iPartidoController.simularPartido(partido);
		
		Gson  g = new Gson();
		String r = g.toJson(resumenPartido);
		
		return r;
	}
	
	@POST
	@Path("configurarCambiosPartido")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public String configurarCambiosPartido(String datos) throws JSONException
	{
		JSONObject datosPartido = new JSONObject(datos);
		Gson g = new Gson();
		String partido = datosPartido.getString("partido");
		DataCambio cambio1 = g.fromJson(datosPartido.getString("cambio1"), DataCambio.class);
		DataCambio cambio2 = g.fromJson(datosPartido.getString("cambio2"), DataCambio.class);
		DataCambio cambio3 = g.fromJson(datosPartido.getString("cambio3"), DataCambio.class);
		DataCambio[] cambios = {cambio1, cambio2, cambio3};
		iPartidoController.configurarCambiosPartido(partido, cambios);
		
		JSONObject json = new JSONObject();
		try
		{
			json.put("cambios_realizados", "ok");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return json.toString();
	}
	
}
