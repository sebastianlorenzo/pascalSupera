package negocio;

import java.util.List;

import javax.ejb.*;

import persistencia.UsuarioDAO;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiciosSeguridadImpl implements ServiciosSeguridad{

	@EJB
	private UsuarioDAO usuarioDAO;
	
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
		Usuario u = new Usuario(2, "juan", "123", "j@h.com", "equipo");
		usuarioDAO.insert(u);
	}
	

}
