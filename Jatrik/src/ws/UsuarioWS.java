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
    public String registrarUsuario(JSONObject datosUsr) throws JSONException{	
    	String login         = (String) datosUsr.get("login");
    	String password      = (String) datosUsr.get("password");
    	String mail          = (String) datosUsr.get("mail");
    	String nombreEquipo  = (String) datosUsr.get("nombreEquipo");
    	String pais          = (String) datosUsr.get("pais");
    	String localidad     = (String) datosUsr.get("localidad");
    	String nombreEstadio = (String) datosUsr.get("nombreEstadio");
    	
    	return iUsuarioController.ingresarUsuario(login, password, mail, nombreEquipo, pais, localidad, nombreEstadio).toString();
    	
    }
    
    
    @GET
    @Path("pruebaRegistrar")
    public String registrar() throws JSONException {
    	JSONObject datosUsr = new JSONObject();
		datosUsr.put("login", "nomUsr");
		datosUsr.put("password", "passUsr");
		datosUsr.put("mail", "mailUsr");
		datosUsr.put("nombreEquipo", "equipoUsr");
		datosUsr.put("pais", "paisUsr");
		datosUsr.put("localidad", "localidadUsr");
		datosUsr.put("nombreEstadio", "estadioUsr");
		return registrarUsuario(datosUsr);
    }
    
    @GET
    @Path("print/{param}")
    @Produces(MediaType.APPLICATION_JSON)
	public String printMessage(@PathParam("param") String msg) {
 
		String result = "{\"result\":\"" + msg+"\"}";
 
		return result;
 
	}
    
}
