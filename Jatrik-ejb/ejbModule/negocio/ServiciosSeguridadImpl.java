package negocio;

import java.util.List;

import javax.ejb.*;

import persistencia.UsuarioDAO;
import persistencia.UsuarioDAOImpl;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiciosSeguridadImpl implements ServiciosSeguridad{

	@EJB
	private UsuarioDAO usuarioDAO;
	
	public ServiciosSeguridadImpl() {
		this.usuarioDAO = new UsuarioDAOImpl();
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean existeUsuario(String login, String password) 
	{
		//try
		//{
			List<Usuario> usuarios = this.usuarioDAO.buscarUsuario(login, password);
			return usuarios != null && usuarios.size() > 0;
		/*}
		catch (Throwable ex)
		{
			throw ExceptionManager.process(ex);
		}*/
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void agregarUsuario(){
		Usuario u = new Usuario(2, "aa", "aa");
		usuarioDAO.insert(u);
	}
	

}
