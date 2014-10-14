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
		try
		{
			Usuario usuario = this.usuarioDAO.buscarUsuario(login, password);
			return usuario != null;
		}
		catch (Throwable ex)
		{
			System.out.println("EXCEPCIÓN: " + ex.getClass());
            return null;
		}
	}
	
}
