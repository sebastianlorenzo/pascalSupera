package ws;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import negocio.IEquipoController;

@ManagedBean
@ApplicationScoped
@Path("/equipos")
public class EquipoWS
{
	
	@EJB
	private  IEquipoController iEquipoController;
	
	@POST
	@Path("listarPaises")
	@Produces(MediaType.APPLICATION_JSON)
	public String listarPaises()
	{
		JSONObject respuesta = new JSONObject();		
		try 
		{
			JSONArray jpaises = null;
			jpaises = iEquipoController.obtenerPaisesInicial();
			respuesta.put("paises", jpaises);

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return respuesta.toString();
	}
		
}
