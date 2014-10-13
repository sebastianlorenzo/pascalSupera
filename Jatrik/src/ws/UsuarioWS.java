package ws;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import persistencia.UsuarioDAO;
import dominio.Equipo;
import dominio.Usuario;
import negocio.IUsuarioController;

@ManagedBean
@ViewScoped
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioWS {
	@EJB
	private  IUsuarioController iusuarioController;
	@EJB
	private UsuarioDAO usuarioDAO;
	
    @GET
    @Path("login/{nomUsuario}/{passUsuario}")
    public String getLoginUsuario(@PathParam("nomUsuario") String nomUsuario, @PathParam("passUsuario") String passUsuario) {
    	Boolean b =  iusuarioController.existeUsuario(nomUsuario, passUsuario);
    	JSONObject json = new JSONObject();
    	 try{
             json.put("respuesta", b);
         }
         catch(Exception ex){
             ex.printStackTrace();
         }
         return json.toString();
    }
    
    @GET
    /*@Path("registrar/")
    public String registrarUsuario(MultivaluedMap<String, String> datosUsr){
    	String login        = datosUsr.getFirst("login");
    	String password     = datosUsr.getFirst("password");
    	String mail         = datosUsr.getFirst("mail");
    	String nombreEquipo = datosUsr.getFirst("nombreEquipo");*/
    @Path("registrar/{login}/{password}/{mail}/{nombreEquipo}/{pais}/{localidad}")
    public String registrarUsuario(@PathParam("login") String login, @PathParam("password") String password, 
    							   @PathParam("mail") String mail, @PathParam("nombreEquipo") String nombreEquipo,
    							   @PathParam("pais") String pais, @PathParam("localidad") String localidad){
    	
    	/*Cambiar esto por la función registrarUsuario */
    	Boolean b = this.iusuarioController.ingresarUsuario(login, password, mail, nombreEquipo, pais, localidad);
    	
    	/*Gson g = new Gson();
    	return g.toJson(usrPersist);*/
    	JSONObject json = new JSONObject();
   	 	try{
            json.put("respuesta", b);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return json.toString();
    }
    
}
