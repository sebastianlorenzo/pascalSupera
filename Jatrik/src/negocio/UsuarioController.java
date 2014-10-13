package negocio;

import java.util.List;

import javax.ejb.*;

import persistencia.UsuarioDAO;
import persistencia.UsuarioDAOImpl;
import dominio.Equipo;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioController implements IUsuarioController{

	@EJB
	private UsuarioDAO usuarioDAO;
	
	@EJB
	private  IEquipoController iEquipoController;
	
	public UsuarioController() {
		this.usuarioDAO = new UsuarioDAOImpl();
		this.iEquipoController = new EquipoController();
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean existeUsuario(String login, String password) 
	{
		try
		{
			List<Usuario> usuarios = this.usuarioDAO.buscarUsuario(login, password);
			return usuarios != null && usuarios.size() > 0;
		}
		catch (Throwable ex)
		{
			System.out.println("EXCEPCIÓN: " + ex.getClass());
            return null;
		}
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean ingresarUsuario(String login, String password, String mail,
			String equipo, String pais, String localidad) {
		
		Boolean encontreUsuario = this.usuarioDAO.existeUsuarioRegistrado(login);
		Boolean encontreEquipo = this.iEquipoController.existeEquipoRegistrado(equipo);
		
		if((encontreUsuario) || (encontreEquipo)){
			return false;
		}
		
		Usuario u = new Usuario(login, password, mail);
		Equipo nuevoequipo = this.iEquipoController.crearEquipo(equipo, pais, localidad);
		u.setEquipo(nuevoequipo);
		this.usuarioDAO.insertarUsuario(u);
		return true;
	}
		

}
