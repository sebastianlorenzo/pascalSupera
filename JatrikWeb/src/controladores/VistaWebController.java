package controladores;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class VistaWebController {

	private String REST_URI_PATH="http://localhost:8080/Jatrik/rest/ws/";
	private String SALUDO_PATH = "login";
	
	public boolean checkLogin (String nom, String pwd) throws JSONException {
		
		Client client = ClientBuilder.newClient();
	    String name = client.target(REST_URI_PATH+SALUDO_PATH+nom+pwd).request(MediaType.APPLICATION_JSON).get(String.class);
	    System.out.println(name);
	    
		// aca hay que hacer un cliente rest y llamar al web services que implementa el check in
	    JSONObject respuesta = new JSONObject(name);
	    System.out.println(respuesta);
	    /*Boolean ok = respuesta.getBoolean("respuesta");
		if (ok)
			return true;
		else
			return false;
		
		*/ return true;
	}
	
	public boolean existeUsuario (String nom){
		
		if (nom.equals("juanfra2") || nom.equals("juanfra"))
			return true;
		else
			return false;
	}
	
	public String registrarUsuario(String nombre,String pwd, String mail, String nomEquio,String nomEstadio, String pais){
		boolean ok = true; // aca llamar al web service de registar
		if (ok)
			return "/paginas/login.xhtml?faces-redirect=true";
		else
			return "index";
	}

	public void saludo () throws JSONException {		        
		
	    Client client = ClientBuilder.newClient();
	    String name = client.target(REST_URI_PATH+SALUDO_PATH).request(MediaType.APPLICATION_JSON).get(String.class);
	    System.out.println(name);
	
	
	}
}
