package ws;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import negocio.IPartidoController;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import com.google.gson.Gson;
import tipos.DataCambio;
import tipos.DataListaPartido;

@ManagedBean
@ApplicationScoped
@Path("/partidos")
public class PartidoWS
{

	@EJB
	private  IPartidoController iPartidoController;
	
	@POST
	@Path("configurarCambiosPartido")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public String configurarCambiosPartido(String datos) throws JSONException
	{
		JSONObject datosPartido = new JSONObject(datos);
		Gson g = new Gson();
		String partido = datosPartido.getString("partido");
		DataCambio cambio1 = g.fromJson(datosPartido.getString("cambio1"), DataCambio.class); // Setear el partido para todos estos
		DataCambio cambio2 = g.fromJson(datosPartido.getString("cambio2"), DataCambio.class);
		DataCambio cambio3 = g.fromJson(datosPartido.getString("cambio3"), DataCambio.class);
		DataCambio[] cambios = {cambio1, cambio2, cambio3};
		iPartidoController.configurarCambiosPartido(partido, cambios);
		
		JSONObject json = new JSONObject();
		try
		{
			json.put("cambios_realizados", "ok");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return json.toString();
	}
	
	//Previamente obtener el nombre del campeonato en ejecución
	@POST
	@Path("listarPartidosMapa")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public String listarPartidosMapa(String datos) throws JSONException
	{
		JSONObject datosPartido = new JSONObject(datos);
		String nomCampeonato          = datosPartido.getString("nomCampeonato");
		
		JSONObject respuesta = new JSONObject();		
		try 
		{
			JSONArray partidos = null;
			partidos = iPartidoController.obtenerPartidosPorZona(nomCampeonato);
			respuesta.put("partidos", partidos);

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return respuesta.toString();
	}
	
	//previamente llamar a listar partidos en ejecución y finalizados para obtener el nombre del campeonato
	@POST
	@Path("listarPartidosJugados")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public String listarPartidosCampeonatoJugados(String datos) throws JSONException
	{
		JSONObject datosPartido = new JSONObject(datos);
		String nomCampeonato    = datosPartido.getString("nomCampeonato");
		
		Gson g = new Gson();
		DataListaPartido dataPartidos = iPartidoController.listarPartidosJugados(nomCampeonato);
		return g.toJson(dataPartidos);
	}
	
	@POST
	@Path("listarMisProximosPartidos")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public String listarMisProximosPartidos(String datos) throws JSONException
	{
		JSONObject datosPartido = new JSONObject(datos);
		String nomEquipo    = datosPartido.getString("nombreEquipo");
		
		JSONObject respuesta = new JSONObject();		
		try 
		{
			JSONArray partidos = null;
			partidos = iPartidoController.obtenerMisPartidosPorJugar(nomEquipo);
			respuesta.put("partidos", partidos);

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return respuesta.toString();
	}
	
	@POST
	@Path("simularPartido")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public String simularPartido(String datos) throws JSONException
	{
		JSONObject datosPartido = new JSONObject(datos);
		String partido          = datosPartido.getString("partido");
		
		iPartidoController.simularPartido(partido);
		
		Gson  g = new Gson();
		String r = g.toJson("ok");
		
		return r;
	}
	
}
