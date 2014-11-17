package controladores;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import tipos.DataListaPosicion;

public class VistaWebController {

	private String REST_URI_PATH="http://localhost:8080/Jatrik/rest/";
	private String MAP_GOOGLE_URI_PATH="http://maps.googleapis.com/maps/api/geocode/json?";
	
	//*************************USUARIO***************************************************************
	private String LOGIN_PATH = "usuarios/login";
	private String LOGOUT_PATH = "usuarios/logout";
	private String LISTA_DESCONECTADOS_PATH = "usuarios/listarDesconectados";
	private String ENVIAR_CHAT_PATH = "usuarios/enviarChat";
	private String REGISTRO_PATH = "usuarios/registrar";
	private String VER_RANKING_PATH = "usuarios/verRanking";
	private String VER_PERFIL_PATH = "usuarios/verPerfil";	
	private String VER_NOTIFICACIONES_PATH = "usuarios/verNotificaciones";
	//*************************CAMPEONATO***************************************************************
	private String CREAR_CAMPEONATO_PATH = "campeonatos/crear";
	private String LISTAR_CAMPEONATOS_PATH = "campeonatos/listarCampeonatos";
	private String ANOTARME_CAMPEONATO_PATH = "campeonatos/inscribirse";
	private String CAMPEONATO_EN_EJECUCION_PATH = "campeonatos/campeonatosEnEjecucion";
	//*************************EQUIPO***************************************************************
	private String OBTENER_TITULARES_PATH = "equipos/obtenerJugadoresTitulares";
	private String OBTENER_SUPLENTES_PATH = "equipos/obtenerJugadoresSuplentes";
	private String OBTENER_TACTICA_PATH = "equipos/obtenerTactica";
	private String MODIFICAR_TACTICA_PATH = "equipos/modificarTactica";
	private String MODIFICAR_TITULARES_PATH = "equipos/modificarJugadoresTitulares";
	private String OBTENER_ENTRENAMIENTO_PATH = "equipos/obtenerEntrenamiento";
	private String MODIFICAR_ENTRENAMIENTO_PATH = "equipos/modificarEntrenamiento";
	private String LISTAR_EQUIPOS_MAPA_PATH = "equipos/listarEquiposMapa";
	private String LISTAR_EQUIPOS_PATH = "equipos/listarEquipos";
	private String REALIZAR_OFERTA_PATH = "equipos/realizarOferta";
	private String OBTENER_OFERTAS_PATH = "equipos/obtenerOfertas";
	private String OBTENER_OFERTAS_REALIZADAS_PATH = "equipos/obtenerOfertasRealizadas";	
	private String ACEPTAR_OFERTA_PATH = "equipos/aceptarOferta";
	private String RECHAZAR_OFERTA_PATH = "equipos/rechazarOferta";	
	//*************************PARTIDOS***************************************************************
	private String LISTAR_PROXIMOS_PARTIDOS_PATH = "partidos/listarMisProximosPartidos";
	private String LISTAR_PARTIDOS_JUGADOS_PATH = "partidos/listarPartidosJugados";
	private String CONFIRMAR_CAMBIOS_PATH = "partidos/configurarCambiosPartido";	
	
	//*************************CAMPEONATOS***************************************************************
	private String LISTAR_CAMPEONATOS_EN_EJECUCION_Y_FINALIZADOS_PATH = "campeonatos/listarCampeonatosEnEjecucionYFinalizados";
	
	//*************************INFO***************************************************************
	private String OBTENER_INFO_RSS_PATH = "equipos/verInfoMobile";
	
	// LOGIN USUARIO	*******************************************************************************
	public String login (String nom, String pwd) {
		
		String envio= "{"+
				"login"+":"+nom+","+
				"password"+":"+pwd+
			   "}";		
		Client client = ClientBuilder.newClient();		
		WebTarget target = client.target(REST_URI_PATH+LOGIN_PATH);	 
		String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
		 
	    return respuesta;
		
	}
	
	// LOGOUT USUARIO	*******************************************************************************
	public boolean logout (String nom,String desconectados, boolean admin) {
		
		
		String envio= "{"+
				"logout"+":"+nom+','+
				"desconectados"+":"+desconectados+','+
				"admin"+":"+admin+','+
			   "}";
		
		Client client = ClientBuilder.newClient();		
		WebTarget target = client.target(REST_URI_PATH+LOGOUT_PATH);	 
		String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
		 
	    JSONObject json=null;
		
			try {
				json = new JSONObject(respuesta);
				boolean ok = json.getBoolean("logout");
				if (ok)
					return true;
				else
					return false;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return false;
		
	}
	
	public boolean existeUsuario (String nom){
		
		if (nom.equals("juanfra2") || nom.equals("juanfra"))
			return true;
		else
			return false;
	}
	
	// REGISTRAR USUARIO	******************************************************************************
	public String registrarUsuario(String nombre,String pwd, String mail, String nomEquipo,String nomEstadio, String pais){
		
		String envio= "{"+
						"login"+":"+nombre+","+
						"password"+":"+pwd+","+
						"mail"+":"+mail+","+
						"nombreEquipo"+":"+nomEquipo+","+
						"pais"+":"+pais+","+
						"localidad"+":"+"localidadUsr"+","+
						"nombreEstadio"+":"+nomEstadio+
					   "}";
		String respuesta_bean;
		Client client = ClientBuilder.newClient();		
	    WebTarget target = client.target(REST_URI_PATH+REGISTRO_PATH);	 
	    String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);  
	    JSONObject json=null;			
		try {
			json = new JSONObject(respuesta);
			boolean ok = json.getBoolean("registrado");
				if (ok){
					respuesta_bean="{"+
									"ok"+":"+true+","+
									"mensaje"+":"+"Ya puedes Iniciar Sesión."+
									"}";				
					
				}
				else
					respuesta_bean="{"+	
							"ok"+":"+true+","+
							"mensaje"+":"+json.getString("mensaje")+
							"}";		
			
			return respuesta_bean;	
				
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}			
	
		return respuesta_bean="{"+
				"ok"+":"+false+","+
				"mensaje"+":"+"Ha ocurrido un error."+
				"}";
		
	}
	
	// ENVIAR CHAT USUARIO	*******************************************************************************
		public String enviarChat (String emisor, String receptor, String mensaje) {
			
			String envio= "{"+
					"emisor"+":"+emisor+","+
					"receptor"+":"+receptor+","+
					"mensaje"+":"+mensaje+
				   "}";		
			Client client = ClientBuilder.newClient();		
			WebTarget target = client.target(REST_URI_PATH+ENVIAR_CHAT_PATH);	 
			String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);			 
		    return respuesta;
			
		}
		
		// VER RANKING	**************************************************************************************
		public String verRanking () {
			
			Client client = ClientBuilder.newClient();		
			WebTarget target = client.target(REST_URI_PATH+VER_RANKING_PATH);	 
			String respuesta=target.request().get(String.class);
		    return respuesta;
			
		}
		// VER PERFIL USUARIO	*******************************************************************************
				public String verPerfil (String nomUsuario) {
					
					String envio= "{"+
							"nomUsuario"+":"+nomUsuario+
						   "}";		
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+VER_PERFIL_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);			 
				    return respuesta;
					
				}
	
	// CREAR CAMPEONATO	**************************************************************************************
		public String crearCampeonato (String nombre,Integer cantidadEquipos ,Date fechaInicio) {
			// formato fecha dd/mm/aaaa
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");			
			String fecha = sdf.format(fechaInicio);
			String dia = fecha.substring(0, fecha.indexOf("/"));
			String mes = fecha.substring(fecha.indexOf("/")+1, fecha.lastIndexOf("/"));
			String anio = fecha.substring(fecha.lastIndexOf("/")+1,fecha.length());
			String envio= "{"+
					"nomCampeonato"+":"+nombre+","+
					"dia"+":"+dia+","+
					"mes"+":"+mes+","+
					"anio"+":"+anio+","+
					"cantidadEquipos"+":"+cantidadEquipos.toString()+
				   "}";					
			
			Client client = ClientBuilder.newClient();		
			WebTarget target = client.target(REST_URI_PATH+CREAR_CAMPEONATO_PATH);	 
			String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
			 
		    return respuesta;
			
		}
		
		// LISTAR CAMPEONATOS	**************************************************************************************
				public String listarCampeonatos () {
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+LISTAR_CAMPEONATOS_PATH);	 
					String respuesta=target.request().get(String.class);
				    return respuesta;
					
				}
		
		// ANOTARSE A CAMPEONATO	*******************************************************************************
				public String anotarseCampeonato (String nomUsr, String nomCampeonato) {
					
					String envio= "{"+
							"nomUsuario"+":"+nomUsr+","+
							"nomCampeonato"+":"+nomCampeonato+
						   "}";		
										
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+ANOTARME_CAMPEONATO_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
					 
				    return respuesta;
					
			}
				
			// LISTAR CAMPEONATOS	**************************************************************************************
				public String campeonatosEnEjecucion () {
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+CAMPEONATO_EN_EJECUCION_PATH);	 
					String respuesta=target.request().get(String.class);
				    return respuesta;
					
				}
			// LISTAR DESCONECTADOS	**************************************************************************************
				public String listarDesconectados (String nomUsuario) {
					
					String envio= "{"+
							"nomUsuario"+":"+nomUsuario+
						   "}";		
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+LISTA_DESCONECTADOS_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
					 
				    return respuesta;
					
				}
				
				// OBTENER JUGADORES TITULARES	*******************************************************************************
				public String obtenerTitulares (String nombreEquipo) {
					
					String envio= "{"+
							"Nombre"+":"+nombreEquipo+
						   "}";		
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+OBTENER_TITULARES_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
					 
				    return respuesta;
					
				}
				// OBTENER JUGADORES SUPLENTES	*******************************************************************************
				public String obtenerSuplentes (String nombreEquipo) {
					
					String envio= "{"+
							"Nombre"+":"+nombreEquipo+
						   "}";		
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+OBTENER_SUPLENTES_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
					 
				    return respuesta;
					
				}
				// OBTENER TACTICA	*******************************************************************************
				public String obtenerTactica (String nombreEquipo) {
					
					String envio= "{"+
							"Nombre"+":"+nombreEquipo+
						   "}";		
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+OBTENER_TACTICA_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
					 
				    return respuesta;
					
				}
				// MODIFICAR TACTICA	*******************************************************************************
				public String modificarTactica (String nombreEquipo,String defensa, String mediocampo, String ataque) {
					
					String envio= "{"+
							"Nombre"+":"+nombreEquipo+","+
							"Ataque"+":"+ataque+","+
							"Defensa"+":"+defensa+","+
							"Mediocampo"+":"+mediocampo							+
						   "}";		
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+MODIFICAR_TACTICA_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
					 
				    return respuesta;
					
				}
				// MODIFICAR TITULARES	*******************************************************************************
				public String modificarTitulares (String nombreEquipo, DataListaPosicion dlp) {
					Gson g = new Gson();
					String posiciones = g.toJson(dlp);
					String envio= "{"+
							"Nombre"+":"+nombreEquipo+","+
							"Posiciones"+":"+posiciones+
						   "}";		
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+MODIFICAR_TITULARES_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
					 
				    return respuesta;
					
				}
				// OBTENER ENTRENAMIENTO	*******************************************************************************
				public String obtenerEntrenamiento (String nomEquipo) {
			
					String envio= "{"+
							"Nombre"+":"+nomEquipo+
						   "}";		
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+OBTENER_ENTRENAMIENTO_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
					 
				    return respuesta;
					
				}
				
				// MODIFICAR ENTRENAMIENTO	*******************************************************************************
				public String modificarEntrenamiento (String nomEquipo,String porteria, String defensivo, 
						String fisico, String ofensivo) {
					String envio= "{"+
							"Nombre"+":"+nomEquipo+","+
							"Porteria"+":"+porteria+","+
							"Defensivo"+":"+defensivo+","+
							"Fisico"+":"+fisico+","+
							"Ofensivo"+":"+ofensivo+
						   "}";		
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+MODIFICAR_ENTRENAMIENTO_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
					 
				    return respuesta;
					
				}
				
				// OBTENER INFO RSS************************************************************************
				public String obtenerInfoRss () {
					
					String envio= "{nomUsuario:\"null\"}";		
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+OBTENER_INFO_RSS_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
				    return respuesta;
					
				}
				// LISTAR EQUIPOS MAPA	**************************************************************************************
				public String listarEquiposMapa () {
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+LISTAR_EQUIPOS_MAPA_PATH);	 
					String respuesta=target.request().get(String.class);
				    return respuesta;
				   /* { equipos: [4] 
				    0: { equipo: "equipoUsr" pais: "Argentina" localidad: "Buenos Aires" }
				    -1: {equipo: "equipoUsrA" pais: "Uruguay" localidad: "Canelones" }
				    - 2: { equipo: "equipoUsrB" pais: "paisUsr" localidad: "localidadUsr" }
				    - 3: { equipo: "equipoUsrC" pais: "Uruguay" localidad: "Montevideo" }-}*/
				}
				
				public JSONArray obtenerLatLong(JSONArray lugares) throws JSONException{
					
					Client client = ClientBuilder.newClient();							
					JSONArray lista = new JSONArray();
					for(int i=0; i<lugares.length();i++){
						JSONObject j = (JSONObject) lugares.get(i);
						String p = j.getString("pais");
						WebTarget target = client.target(MAP_GOOGLE_URI_PATH).queryParam("address", p);
						System.out.println(p);
						String respuesta = target.request(MediaType.APPLICATION_JSON).get(String.class);
						JSONObject jo = new JSONObject(respuesta);
						if (jo.get("status").equals("OK")){
				            JSONArray jsonObject1 = (JSONArray) jo.get("results");
				            JSONObject jsonObject2 = (JSONObject)jsonObject1.get(0);
				            JSONObject jsonObject3 = (JSONObject)jsonObject2.get("geometry");
				            JSONObject location = (JSONObject) jsonObject3.get("location");
				            System.out.println( "Lat = "+location.get("lat"));
				            System.out.println( "Lng = "+location.get("lng"));
				            JSONObject nuevo = new JSONObject();
				            nuevo.put("lat",location.get("lat"));
				            nuevo.put("lng",location.get("lng"));
				            lista.put(nuevo);
						}
						
					}
					return lista;
					
				}
				
				// OBTENER LISTA DE EQUIPOS ************************************************************************
				public String obtenerEquipos (String nombreEquipo) {
					
					String envio= "{nombreEquipo:"+nombreEquipo+"}";		
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+LISTAR_EQUIPOS_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
				    return respuesta;
					
				}
				
				// REALIZAR OFERTA POR JUGADOR ************************************************************************
				public String realizarOferta (String nombreUsr, String idJugador, String comentario, String precio) {
					
					String envio= "{"+
							"nomUsuario"+":"+nombreUsr+","+
							"idJugador"+":"+idJugador+","+
							"precio"+":"+precio+","+
							"comentario"+":"+comentario+
						   "}";
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+REALIZAR_OFERTA_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
				    return respuesta;
					
				}
	
				// OBTENER OFERTAS ************************************************************************
				public String obtenerOfertas (String nombreUsr) {
					
					String envio= "{"+
							"nomUsuario"+":"+nombreUsr+
						   "}";
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+OBTENER_OFERTAS_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
				    return respuesta;
					
				}
				// OBTENER OFERTAS REALIZADAS************************************************************************
				public String obtenerOfertasRealizadas (String nombreUsr) {
					
					String envio= "{"+
							"nomUsuario"+":"+nombreUsr+
						   "}";
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+OBTENER_OFERTAS_REALIZADAS_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
				    return respuesta;
					
				}
				// ACEPTAR OFERTA ************************************************************************
				public String aceptarOferta (String nomUsuario,String comentario,String idOferta) {
					
					String envio= "{"+
							"nomUsuario"+":"+nomUsuario+","+
							"comentario"+":"+comentario+","+
							"idOferta"+":"+idOferta+
						   "}";
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+ACEPTAR_OFERTA_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
				    return respuesta;
					
				}
				// RECHAZAR OFERTA ************************************************************************
				public String rechazarOferta (String nomUsuario,String comentario,String idOferta) {
					
					String envio= "{"+
							"nomUsuario"+":"+nomUsuario+","+
							"comentario"+":"+comentario+","+
							"idOferta"+":"+idOferta+
						   "}";
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+RECHAZAR_OFERTA_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
				    return respuesta;
					
				}	
				// LISTAR PROXIMOS PARTIDOS************************************************************************
				public String listarProximosPartidos (String nombreEquipo) {
					
					String envio= "{"+
							"nombreEquipo"+":"+nombreEquipo+
						   "}";
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+LISTAR_PROXIMOS_PARTIDOS_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
				    return respuesta;
					
				}	
				
				// LISTAR CAMPEONATOS FINALIZADOS Y EN EJECUCION *************************************************************************************
				public String listarCampeonatosFinalizadosEjecucion () {
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+LISTAR_CAMPEONATOS_EN_EJECUCION_Y_FINALIZADOS_PATH);	 
					String respuesta=target.request().get(String.class);
				    return respuesta;
					
				}
				// LISTAR PARTIDOS YA JUGADOS ************************************************************************
				public String listarPartidosJugados (String nomCampeonato) {
					
					String envio= "{"+
							"nomCampeonato"+":"+nomCampeonato+
						   "}";
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+LISTAR_PARTIDOS_JUGADOS_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
				    return respuesta;
					
				}
				// CONFIRMAR CAMBIOS ************************************************************************
				public String confirmarCambios (String nomPartido,String cambio1,String cambio2,String cambio3) {
					
					String envio= "{"+
							"partido"+":"+nomPartido+","+
							"cambio1"+":"+cambio1+","+
							"cambio2"+":"+cambio2+","+
							"cambio3"+":"+cambio3+
						   "}";
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+CONFIRMAR_CAMBIOS_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
				    return respuesta;
					
				}
				// VER NOTIFICACIONES	*******************************************************************************
				public String verNotificaciones (String nomUsuario) {
					
					String envio= "{"+
							"nomUsuario"+":"+nomUsuario+
						   "}";		
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH + VER_NOTIFICACIONES_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);			 
				    return respuesta;
					
				}

				//LISTAR JUGADORES DE MI EQUIPO
				private String LISTAR_JUGADORES_PATH = "equipos/listarEstadisiticasJugadoresEquipo";
				public String listarJugadoresDeMiEquipo (String nombreEquipo) {
					
					String envio= "{"+
							"nombreEquipo"+":"+nombreEquipo+
						   "}";
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+LISTAR_JUGADORES_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
				    return respuesta;
					
				}	
				
				//VER PARTIDOS MAPA
				private String LISTAR_PARTIDOS_MAPA_PATH = "partidos/listarPartidosMapa";
				public String listarPartidosMapa (String nomCampeonato) {
					
					String envio= "{"+
							"nomCampeonato"+":"+nomCampeonato+
						   "}";
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+LISTAR_PARTIDOS_MAPA_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
				    return respuesta;
					
				}

}

