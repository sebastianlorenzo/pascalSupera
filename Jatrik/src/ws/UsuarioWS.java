package ws;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import negocio.IUsuarioController;

@ManagedBean
@ApplicationScoped
@Path("/ws")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioWS {
	
	@EJB
	private  IUsuarioController iUsuarioController;
	
    @GET
    @Path("login/{nomUsuario}/{passUsuario}")
    public String getLoginUsuario(@PathParam("nomUsuario") String nomUsuario, @PathParam("passUsuario") String passUsuario) {
    	Boolean b =  iUsuarioController.existeUsuario(nomUsuario, passUsuario);
    	JSONObject json = new JSONObject();
    	 try{
             json.put("respuesta", b);
         }
         catch(Exception ex){
             ex.printStackTrace();
         }
         return json.toString();
    }
    
    @POST
    @Path("registrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public String registrarUsuario(String datos) throws JSONException{
    JSONObject datosUsr = new JSONObject(datos);
    	String login         = datosUsr.getString("login");
    	String password      = datosUsr.getString("password");
    	String mail          = datosUsr.getString("mail");
    	String nombreEquipo  = datosUsr.getString("nombreEquipo");
    	String pais          = datosUsr.getString("pais");
    	String localidad     = datosUsr.getString("localidad");
    	String nombreEstadio = datosUsr.getString("nombreEstadio");
    	
    	return iUsuarioController.ingresarUsuario(login, password, mail, nombreEquipo, pais, localidad, nombreEstadio).toString();
    	
    }
        
}
