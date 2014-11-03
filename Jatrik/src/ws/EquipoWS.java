package ws;

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
		DataListaEquipo dataEq = iEquipoController.obtenerEquiposData(nombreEq);
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
		
		return iEquipoController.realizarOferta(nomUsuario, idJugador, precio, comentario).toString();
		
	}
	
	//Obtengo las ofertas hechas a mis jugadores, ofertas recibidas
	@POST
	@Path("obtenerOfertas")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String obtenerOfertas(String datos) throws JSONException
	{
		JSONObject datosOferta = new JSONObject(datos);
		String nomUsuario = datosOferta.getString("nomUsuario");
		Gson g = new Gson();
		DataListaOferta dataOf = iEquipoController.obtenerOfertasData(nomUsuario);
		return g.toJson(dataOf);	
	}
	
	@POST
	@Path("aceptarOferta")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String aceptarOferta(String datos) throws JSONException
	{
		JSONObject datosOferta = new JSONObject(datos);
		String nomUsuario = datosOferta.getString("nomUsuario"); //Logueado
		String comentario = datosOferta.getString("comentario");
		Integer idOferta = Integer.parseInt(datosOferta.getString("idOferta"));
		
		return iEquipoController.aceptarOferta(nomUsuario, comentario, idOferta).toString();
		
	}
	
	@POST
	@Path("rechazarOferta")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String rechazarOferta(String datos) throws JSONException
	{
		JSONObject datosOferta = new JSONObject(datos);
		String nomUsuario = datosOferta.getString("nomUsuario"); //Logueado
		String comentario = datosOferta.getString("comentario");
		Integer idOferta = Integer.parseInt(datosOferta.getString("idOferta"));
		
		return iEquipoController.rechazarOferta(nomUsuario, comentario, idOferta).toString();
		
	}
	
	@POST
	@Path("obtenerOfertasRealizadas")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String obtenerOfertasRealizadas(String datos) throws JSONException
	{
		JSONObject datosOferta = new JSONObject(datos);
		String nomUsuario = datosOferta.getString("nomUsuario"); //Logueado
		Gson g = new Gson();
		DataListaOferta dataOf = iEquipoController.obtenerOfertasRealizadas(nomUsuario);
		return g.toJson(dataOf);		
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
	
	@POST
	@Path("obtenerEntrenamiento")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String obtenerEntrenamiento(String datos) throws JSONException
	{
		JSONObject datosEquipo = new JSONObject(datos);
		String nombreEquipo = datosEquipo.getString("Nombre");
		if (iEquipoController.existeEquipoRegistrado(nombreEquipo))
		{
    		return iEquipoController.obtenerEntrenamiento(nombreEquipo).toString();
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
	@Path("modificarEntrenamiento")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String modificarEntrenamiento(String datos) throws JSONException
	{
		JSONObject datosEquipo = new JSONObject(datos);
		String nombreEquipo = datosEquipo.getString("Nombre");
		Integer entrenamientoOfensivo = Integer.parseInt(datosEquipo.getString("Ofensivo"));
		Integer entrenamientoDefensivo = Integer.parseInt(datosEquipo.getString("Defensivo"));
		Integer entrenamientoFisico = Integer.parseInt(datosEquipo.getString("Fisico"));
		Integer entrenamientoPorteria = Integer.parseInt(datosEquipo.getString("Porteria"));
		if (iEquipoController.existeEquipoRegistrado(nombreEquipo))
		{
    		iEquipoController.modificarEntrenamiento(nombreEquipo, entrenamientoOfensivo, entrenamientoDefensivo, entrenamientoFisico, entrenamientoPorteria);
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
	
	@GET
	@Path("ejecutarEntrenamiento")
	public void ejecutarEntrenamiento()
	{
		iEquipoController.ejecutarEntrenamiento();
	}	
}
