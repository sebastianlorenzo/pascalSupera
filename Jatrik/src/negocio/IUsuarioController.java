package negocio;

import java.util.List;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import tipos.DataListaMensaje;
import tipos.DataListaNotificacion;
import tipos.DataUsuario;

@Local
public interface IUsuarioController 
{
	
	public Boolean existeUsuario(String login, String password);
	
	public Boolean estaConectadoUsuario(String login);
	
	public JSONObject ingresarUsuario(String login, String password, String mail, String equipo,
									  String pais, String estadio);
	
	public void setearConectado(String login);
	
	public void setearDesconectado(String login);
	
	public Boolean esAdmin(String login);

	public JSONArray obtenerDesconectados(String login);
	
	public Boolean enviarChat(String emisor, String receptor, String mensaje);

	public DataListaMensaje obtenerMensajes(String receptor);

	public String obtenerEquipo(String login);

	public DataListaNotificacion verNotificaciones(String nomUsuario);

	public void setearNuevosAmigos(String nomUsuario, List<String> listUs);

	public JSONArray obtenerRanking();

	public DataUsuario verPerfil(String nomUsuario);
			
}
