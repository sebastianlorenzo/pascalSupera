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
	
}
