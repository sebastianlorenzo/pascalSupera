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
@Path("/usuarios")
public class UsuarioWS 
{
	
	@EJB
	private  IUsuarioController iUsuarioController;
	
	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public String loginUsuario(String datos) throws JSONException
	{
		JSONObject datosUsr = new JSONObject(datos);
		String login        = datosUsr.getString("login");
		String password     = datosUsr.getString("password");    
		
		Boolean existeUsr = iUsuarioController.existeUsuario(login, password);
		if (existeUsr)
		{
    		iUsuarioController.setearConectado(login);
		}
		
		JSONObject json = new JSONObject();
		try
		{
			json.put("login", existeUsr);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return json.toString();
	}
    
    @POST
    @Path("registrar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String registrarUsuario(String datos) throws JSONException
    {
    	JSONObject datosUsr  = new JSONObject(datos);
    	String login         = datosUsr.getString("login");
    	String password      = datosUsr.getString("password");
    	String mail          = datosUsr.getString("mail");
    	String nombreEquipo  = datosUsr.getString("nombreEquipo");
    	String pais          = datosUsr.getString("pais");
    	String localidad     = datosUsr.getString("localidad");
    	String nombreEstadio = datosUsr.getString("nombreEstadio");
    	
    	return iUsuarioController.ingresarUsuario(login, password, mail, nombreEquipo, pais, localidad, nombreEstadio).toString();    	
    }
    
    
    @POST
	@Path("logout")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String logoutUsuario(String datos) throws JSONException
    {
    	JSONObject datosUsr = new JSONObject(datos);
		String nomUsuario   = datosUsr.getString("login");
		
		Boolean conectadoUsr =  iUsuarioController.estaConectadoUsuario(nomUsuario);
    	if (conectadoUsr)
    	{
    		iUsuarioController.setearDesconectado(nomUsuario);
    	}
    	
    	JSONObject json = new JSONObject();
		try
		{
			json.put("logout", conectadoUsr);
	    }
	    catch(Exception ex)
		{
	    	ex.printStackTrace();
	    }
		
	    return json.toString();
    }
        
}
