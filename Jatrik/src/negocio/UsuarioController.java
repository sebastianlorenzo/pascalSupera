package negocio;

import java.util.List;

import javax.ejb.*;

import persistencia.UsuarioDAO;
import persistencia.UsuarioDAOImpl;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioController implements IUsuarioController{

	@EJB
	private UsuarioDAO usuarioDAO;
	
	public UsuarioController() {
		this.usuarioDAO = new UsuarioDAOImpl();
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean existeUsuario(String login, String password) 
	{
		try
		{
			List<Usuario> usuarios = this.usuarioDAO.buscarUsuario(login);
			return usuarios != null && usuarios.size() > 0;
		}
		catch (Throwable ex)
		{
			System.out.println("EXCEPCIÓN: " + ex.getClass());
            return null;
		}
	}
		

}
