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
	
	@GET
	@Path("listarEquipos")
	@Produces(MediaType.APPLICATION_JSON)
	public String listarEquipos()
	{
		Gson g = new Gson();
		DataListaEquipo dataEq = this.iEquipoController.obtenerEquiposData();
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
		String nomJugador = datosOferta.getString("nomJugador");
		Integer precio = Integer.parseInt(datosOferta.getString("precio"));
		
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
