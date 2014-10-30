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

public class VistaWebController {

	private String REST_URI_PATH="http://localhost:8080/Jatrik/rest/";
	private String LOGIN_PATH = "usuarios/login";
	private String LOGOUT_PATH = "usuarios/logout";
	private String REGISTRO_PATH = "usuarios/registrar";
	private String CREAR_CAMPEONATO_PATH = "campeonatos/crear";
	private String LISTAR_CAMPEONATOS_PATH = "campeonatos/listarCampeonatos";
	private String ANOTARME_CAMPEONATO_PATH = "campeonatos/inscribirse";
	
	// LOGIN USUARIO	*******************************************************************************
	public String login (String nom, String pwd) {
		
		String envio= "{"+
				"login"+":"+nom+","+
				"password"+":"+pwd+
			   "}";		
		
		System.out.println(envio);
		
		Client client = ClientBuilder.newClient();		
		WebTarget target = client.target(REST_URI_PATH+LOGIN_PATH);	 
		String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
		 
	    return respuesta;
		
	}
	
	// LOGOUT USUARIO	*******************************************************************************
	public boolean logout (String nom) {
		
		String envio= "{"+
				"logout"+":"+nom+
			   "}";		
		System.out.println(envio);
		
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
		System.out.println(envio);
		
		Client client = ClientBuilder.newClient();		
	    WebTarget target = client.target(REST_URI_PATH+REGISTRO_PATH);	 
	    String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
	
	    System.out.println(respuesta);   
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
	
	// CREAR CAMPEONATO	**************************************************************************************
		public String crearCampeonato (String nombre,Integer cantidadEquipos ,Date fechaInicio) {
			// formato fecha dd/mm/aaaa
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");			
			String fecha = sdf.format(fechaInicio);
			System.out.println(fecha);
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
			
			System.out.println(envio);
			
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
					System.out.println(respuesta);
				    return respuesta;
					
				}
		
		// ANOTARSE A CAMPEONATO	*******************************************************************************
				public String anotarseCampeonato (String nomUsr, String nomCampeonato) {
					
					String envio= "{"+
							"nomUsuario"+":"+nomUsr+","+
							"nomCampeonato"+":"+nomCampeonato+
						   "}";		
					
					System.out.println(envio);
					
					Client client = ClientBuilder.newClient();		
					WebTarget target = client.target(REST_URI_PATH+ANOTARME_CAMPEONATO_PATH);	 
					String respuesta=target.request(MediaType.APPLICATION_JSON).post(Entity.json(envio),String.class);
					 
				    return respuesta;
					
			}

}
