package persistencia;

import java.util.List;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;

import tipos.DataListaMensaje;
import tipos.DataListaNotificacion;
import dominio.Notificacion;
import dominio.Usuario;

@Local
public interface UsuarioDAO 
{
	
	public Usuario insertarUsuario(Usuario u);
		
	public void borrarUsuario(Usuario u);
		
	public Usuario obtenerUsuario(String login);
	
	public void setearConectado(String login);
	
	public void setearDesconectado(String login);	
	
	public Boolean getEstaConectadoUsuario(String login);
	
	public Usuario buscarUsuario(String login, String password);
	
	public List<Usuario> obtenerTodos();
	
	public Boolean existeUsuarioRegistrado(String login);
	
	public Boolean esAdministrador(String login);
	
	public Boolean enviarChat(String emisor, String receptor, String mensaje);

	public DataListaMensaje recibirChat(String receptor);

	public JSONArray obtenerDesconectados(String login);

	public String obtenerNombreEq(String login);

	public void enviarNotificacion(String nom_usuario, String texto_notificacion);
	
	public DataListaNotificacion obtenerNotificaciones(String login);
	
}
