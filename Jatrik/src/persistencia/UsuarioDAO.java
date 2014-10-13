package persistencia;

import java.util.List;

import javax.ejb.Local;

import dominio.Equipo;
import dominio.Usuario;

@Local
public interface UsuarioDAO {

	
	public Usuario insertarUsuario(Usuario u);
	
	public boolean actualizarUsuario(String login, String password,String mail, Equipo equipo, boolean conectado);
	
	public void borrarUsuario(Usuario u);
		
	public Usuario obtenerUsuario(String login);
	
	public void setearConectado(String login);
	
	public void setearDesconectado(String login);	
	
	public List<Usuario> buscarUsuario(String login, String password);
	
	public List<Usuario> obtenerTodos();
	
}
