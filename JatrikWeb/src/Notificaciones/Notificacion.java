package Notificaciones;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@ManagedBean
@ApplicationScoped
@Path("/notificaciones")
public class Notificacion {
    
	@POST
	@Path("nuevaNotificacion")
    @Consumes(MediaType.APPLICATION_JSON)
	public void nuevaNotificacion(String datos) throws JSONException
	{
		JSONObject datosPartido = new JSONObject(datos);
		String nomUsuario = datosPartido.getString("nomUsuario");
		
		NotifyView nv = new NotifyView();
		nv.setDetail("");
		nv.setSummary("Tiene un nuevo mensaje");
		nv.send(nomUsuario);
	}
	
}