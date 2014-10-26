package negocio;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

@Local
public interface IUsuarioController 
{
	
	public Boolean existeUsuario(String login, String password);
	
	public Boolean estaConectadoUsuario(String login);
	
	public JSONObject ingresarUsuario(String login, String password, String mail, String equipo,
									  String pais, String localidad, String estadio);
	
	public void setearConectado(String login);
	
	public void setearDesconectado(String login);
	
	public Boolean esAdmin(String login);

	public JSONArray obtenerDesconectados();
	
	public Boolean enviarChat(String emisor, String receptor, String mensaje);
}
