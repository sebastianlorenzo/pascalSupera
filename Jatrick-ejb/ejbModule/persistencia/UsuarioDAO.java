package persistencia;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.EntityManager;

import dominio.Usuario;

@Local
public interface UsuarioDAO {

	
	public Usuario insert(Usuario entity);
	
	public void update(Usuario entity);
	
	public void delete(Usuario entity);
	
	public void delete(Integer id);
	
	public Usuario findById(Integer id);
	
	public List<Usuario> findAll();
	
	public List<Usuario> buscarUsuario(String login, String password);
	
}
