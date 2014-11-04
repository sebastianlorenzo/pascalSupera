package controladores;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import dataTypes.DataListaPosicion;

public class VistaWebController {

	private String REST_URI_PATH="http://localhost:8080/Jatrik/rest/";
	
	//*************************USUARIO***************************************************************
	private String LOGIN_PATH = "usuarios/login";
	private String LOGOUT_PATH = "usuarios/logout";
	private String LISTA_DESCONECTADOS_PATH = "usuarios/listarDesconectados";
	private String ENVIAR_CHAT_PATH = "usuarios/enviarChat";
	private String REGISTRO_PATH = "usuarios/registrar";
	//*************************CAMPEONATO***************************************************************
	private String CREAR_CAMPEONATO_PATH = "campeonatos/crear";
	private String LISTAR_CAMPEONATOS_PATH = "campeonatos/listarCampeonatos";
	private String ANOTARME_CAMPEONATO_PATH = "campeonatos/inscribirse";
	private String CAMPEONATO_EN_EJECUCION_PATH = "campeonatos/campeonatosEnEjecucion";
	//*************************EQUIPO***************************************************************
	private String OBTENER_TITULARES_PATH = "equipos/obtenerJugadoresTitulares";
	private String OBTENER_SUPLENTES_PATH = "equipos/obtenerJugadoresSuplentes";
	private String MODIFICAR_TACTICA_PATH = "equipos/modificarTactica";
	private String MODIFICAR_TITULARES_PATH = "equipos/modificarJugadoresTitulares";
	private String ENTRENAR_EQUIPO_PATH = "equipos/";
	
	
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
	public boolean logout (String nom,String desconectados) {
		
		String envio= "{"+
				"logout"+":"+nom+','+
				"desconectados"+":"+desconectados+
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
				// MODIFICAR TACTICA	*******************************************************************************
				public String modificarTactica (String nombreEquipo,String ataque, String defensa, String mediocampo) {
					
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
				// ENTRENAR EQUIPO	*******************************************************************************
				public void entrenarEquipo (String nomEquipo,String golero, String defensa, String mediocampo, String ataque) {
			
					String envio= "{"+
							"Nombre"+":"+nomEquipo+","+
							"Golero"+":"+golero+","+
							"Defensa"+":"+defensa+","+
							"Mediocampo"+":"+mediocampo+","+
							"Ataque"+":"+ataque+
						   "}";		
					System.out.println(envio);
					/*
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+MODIFICAR_TITULARES_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
					 
				    return respuesta;*/
					
				}
				

}
