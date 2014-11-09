package ws;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import tipos.DataListaMensaje;
import tipos.DataListaNotificacion;
import tipos.DataUsuario;

import com.google.gson.Gson;

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
		Boolean es_admin = false;
		String nomEquipo = null;
		Boolean existeUsr = iUsuarioController.existeUsuario(login, password);
		if (existeUsr)
		{
    		iUsuarioController.setearConectado(login);
    		es_admin = iUsuarioController.esAdmin(login);
    		if(es_admin == false)
    			nomEquipo = iUsuarioController.obtenerEquipo(login);
		}
		
		JSONObject json = new JSONObject();
		try
		{
			json.put("login", existeUsr);
			json.put("es_admin", es_admin);
			json.put("nomEquipo", nomEquipo);
			
			if(existeUsr)
			{
				Gson g = new Gson();
				DataListaMensaje mensajesNuevos = iUsuarioController.obtenerMensajes(login);		
				json.put("mensajesNuevos", g.toJson(mensajesNuevos));
			}
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
    	String nombreEstadio = datosUsr.getString("nombreEstadio");
    	
    	return iUsuarioController.ingresarUsuario(login, password, mail, nombreEquipo, pais, nombreEstadio).toString();    	
    }
    
    @POST
	@Path("logout")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String logoutUsuario(String datos) throws JSONException
    {
    	JSONObject datosUsr = new JSONObject(datos);
		String nomUsuario   = datosUsr.getString("logout");
		
		if (!datosUsr.getBoolean("admin")){
			JSONArray amigos = datosUsr.getJSONArray("desconectados");
			List<String> listUs = new ArrayList<String>();
			int i;
			for (i=0; i < amigos.length(); i++){
				JSONObject jsonObject = amigos.getJSONObject(i);
				String us = jsonObject.getString("desconectado");
				listUs.add(us);	
			}
			iUsuarioController.setearNuevosAmigos(nomUsuario, listUs);
		}
		
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
    
    @POST
	@Path("listarDesconectados")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public String listarDesconectados(String datos) throws JSONException
    {
    	JSONObject datosUsr = new JSONObject(datos);
		String nomUsuario   = datosUsr.getString("nomUsuario");
		
		JSONObject respuesta = new JSONObject();		
		try 
		{
			JSONArray jdesconectados = null;
			jdesconectados = iUsuarioController.obtenerDesconectados(nomUsuario);
			respuesta.put("desconectados", jdesconectados);

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return respuesta.toString();
	}

    @POST
	@Path("enviarChat")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String enviarChat(String datos) throws JSONException
    {
    	JSONObject datosM = new JSONObject(datos);
		String emisor   = datosM.getString("emisor");
		String receptor   = datosM.getString("receptor");
		String mensaje   = datosM.getString("mensaje");
		
		Boolean enviado =  iUsuarioController.enviarChat(emisor, receptor, mensaje);
    	
    	JSONObject json = new JSONObject();
		try
		{
			json.put("mensaje", enviado);
	    }
	    catch(Exception ex)
		{
	    	ex.printStackTrace();
	    }
		
	    return json.toString();
    }
    

    @POST
	@Path("verNotificaciones")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String verNotificaciones(String datos) throws JSONException
    {
    	JSONObject datosNotificacion = new JSONObject(datos);
		String nomUsuario = datosNotificacion.getString("nomUsuario"); //Logueado
		Gson g = new Gson();
		DataListaNotificacion dataNotif = iUsuarioController.verNotificaciones(nomUsuario);
		return g.toJson(dataNotif);
    }
    
	@GET
	@Path("verRanking")
	@Produces(MediaType.APPLICATION_JSON)
	public String verRanking() throws JSONException
	{
		JSONObject respuesta = new JSONObject();		
		try 
		{
			JSONArray usuariosRanking = null;
			usuariosRanking = iUsuarioController.obtenerRanking();
			respuesta.put("ranking", usuariosRanking);

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return respuesta.toString();		
	}
	
	@POST
	@Path("verPerfil")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String verPerfil(String datos) throws JSONException
    {
    	JSONObject datosNotificacion = new JSONObject(datos);
		String nomUsuario = datosNotificacion.getString("nomUsuario"); //Logueado
		Gson g = new Gson();
		DataUsuario dataUsuario = iUsuarioController.verPerfil(nomUsuario);
		return g.toJson(dataUsuario);
    }
      
}
