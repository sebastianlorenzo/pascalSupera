package ws;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import negocio.IEquipoController;

@ManagedBean
@ApplicationScoped
@Path("/ws")
@Produces(MediaType.APPLICATION_JSON)
public class EquipoWS {
	@EJB
	private  IEquipoController iEquipoController;
	
	@POST
	@Path("index")
	public String listarPaises(){
		JSONObject respuesta = new JSONObject();
		
		try {
			JSONArray jpaises = null;
			jpaises = iEquipoController.obtenerPaisesInicial();
			respuesta.put("lista", jpaises);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return respuesta.toString();
	}
	
	
}
