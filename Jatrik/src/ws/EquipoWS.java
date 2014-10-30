package ws;

import java.lang.reflect.Type;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import tipos.DataListaEquipo;
import tipos.DataListaPosicion;
import tipos.DataListaOferta;


import com.google.gson.Gson;

import negocio.IEquipoController;

@ManagedBean
@ApplicationScoped
@Path("/equipos")
public class EquipoWS
{
	
	@EJB
	private  IEquipoController iEquipoController;
	
	/*
	@GET
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
	*/
	
	@GET
	@Path("listarEquiposMapa")
	@Produces(MediaType.APPLICATION_JSON)
	public String listarEquiposMapa()
	{
		JSONObject respuesta = new JSONObject();		
		try 
		{
			JSONArray jequipos = null;
			jequipos = iEquipoController.obtenerEquipos();
			respuesta.put("equipos", jequipos);

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return respuesta.toString();
	}
	
	@POST
	@Path("zonaEquipo")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String zonaEquipo(String datos) throws JSONException
    {
    	JSONObject datosE = new JSONObject(datos);
		String nomEquipo   = datosE.getString("equipo");
		return iEquipoController.obtenerZonaEquipo(nomEquipo).toString();
    }
	
	@POST
	@Path("obtenerTactica")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String obtenerTactica(String datos) throws JSONException
	{
		JSONObject datosEquipo = new JSONObject(datos);
		String nombreEquipo = datosEquipo.getString("Nombre");
		if (iEquipoController.existeEquipoRegistrado(nombreEquipo))
		{
    		return iEquipoController.obtenerTactica(nombreEquipo).toString();
		}
		else
		{
			JSONObject jsonResult = new JSONObject();
			try
			{
				jsonResult.put("Result", false);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return jsonResult.toString();
		}
	}
	
	@POST
	@Path("modificarTactica")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String modificarTactica(String datos) throws JSONException
	{
		JSONObject datosEquipo = new JSONObject(datos);
		String nombreEquipo = datosEquipo.getString("Nombre");
		Integer ataqueEquipo = Integer.parseInt(datosEquipo.getString("Ataque"));
		Integer defensaEquipo = Integer.parseInt(datosEquipo.getString("Defensa"));
		Integer mediocampoEquipo = Integer.parseInt(datosEquipo.getString("Mediocampo"));
		if (iEquipoController.existeEquipoRegistrado(nombreEquipo))
		{
    		iEquipoController.modificarTactica(nombreEquipo, ataqueEquipo, mediocampoEquipo, defensaEquipo);
    		JSONObject jsonResult = new JSONObject();
			try
			{
				jsonResult.put("Result", true);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return jsonResult.toString();
		}
		else
		{
			JSONObject jsonResult = new JSONObject();
			try
			{
				jsonResult.put("Result", false);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return jsonResult.toString();
		}
	}
	
	@POST
	@Path("listarEquipos")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String listarEquipos(String datos) throws JSONException
	{
		JSONObject datosEquipo = new JSONObject(datos);
		String nombreEq = datosEquipo.getString("nombreEquipo");
		Gson g = new Gson();
		DataListaEquipo dataEq = this.iEquipoController.obtenerEquiposData(nombreEq);
		return g.toJson(dataEq);
	}
	
	@POST
	@Path("realizarOferta")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String realizarOferta(String datos) throws JSONException
	{
		JSONObject datosOferta = new JSONObject(datos);
		String nomUsuario = datosOferta.getString("nomUsuario");
		Integer idJugador = Integer.parseInt(datosOferta.getString("idJugador"));
		Integer precio = Integer.parseInt(datosOferta.getString("precio"));
		String comentario = datosOferta.getString("comentario");
		
		JSONObject respuesta = new JSONObject();
		try
		{
			Boolean joferta;
			joferta = iEquipoController.realizarOferta(nomUsuario, idJugador, precio, comentario);
			respuesta.put("oferta", joferta);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return respuesta.toString();
	}
	
	@POST
	@Path("obtenerJugadoresTitulares")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String obtenerJugadoresTitulares(String datos) throws JSONException
	{
		JSONObject datosEquipo = new JSONObject(datos);
		String nombreEquipo = datosEquipo.getString("Nombre");
		return iEquipoController.obtenerJugadoresTitulares(nombreEquipo);		
	}
	
	@POST
	@Path("obtenerJugadoresSuplentes")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String obtenerJugadoresSuplentes(String datos) throws JSONException
	{
		JSONObject datosEquipo = new JSONObject(datos);
		String nombreEquipo = datosEquipo.getString("Nombre");
		return iEquipoController.obtenerJugadoresSuplentes(nombreEquipo);		
	}
	
	@POST
	@Path("modificarJugadoresTitulares")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String modificarJugadoresTitulares(String datos) throws JSONException
	{
		JSONObject datosJson = new JSONObject(datos);
		String nomEquipo = datosJson.getString("Nombre");
		String posiciones = datosJson.getString("Posiciones");
		Gson g = new Gson();
		DataListaPosicion dlp = g.fromJson(posiciones, DataListaPosicion.class);
		JSONObject respuesta = new JSONObject();
		if (iEquipoController.modificarJugadoresTitulares(nomEquipo, dlp))
			respuesta.put("Resultado", true);
		else 
			respuesta.put("Resultado", false);
		return respuesta.toString();
	}
	
	//Obtengo las ofertas realizadas a los jugadores pertenecientes a mi equipo
	@POST
	@Path("obtenerOfertas")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String obtenerOfertas(String datos) throws JSONException
	{
		JSONObject datosOferta = new JSONObject(datos);
		String nomUsuario = datosOferta.getString("nomUsuario");
		Gson g = new Gson();
		DataListaOferta dataOf = this.iEquipoController.obtenerOfertasData(nomUsuario);
		return g.toJson(dataOf);	
	}
		
}
