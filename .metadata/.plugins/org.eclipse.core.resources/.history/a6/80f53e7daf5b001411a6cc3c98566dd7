package persistencia;

import java.util.List;
import javax.ejb.Local;
import dominio.Equipo;
import dominio.Usuario;

@Local
public interface UsuarioDAO 
{
	
	public Usuario insertarUsuario(Usuario u);
	
	public Boolean actualizarUsuario(String login, String password,String mail, Equipo equipo, Boolean conectado);
	
	public void borrarUsuario(Usuario u);
		
	public Usuario obtenerUsuario(String login);
	
	public void setearConectado(String login);
	
	public void setearDesconectado(String login);	
	
	public Boolean getEstaConectadoUsuario(String login);
	
	public Usuario buscarUsuario(String login, String password);
	
	public List<Usuario> obtenerTodos();
	
	public Boolean existeUsuarioRegistrado(String login);
	
	public Boolean esAdministrador(String login);
	
}
