package negocio;

import javax.ejb.*;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import persistencia.UsuarioDAO;
import persistencia.UsuarioDAOImpl;
import dominio.Equipo;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioController implements IUsuarioController
{

	@EJB
	private UsuarioDAO usuarioDAO;
	
	@EJB
	private  IEquipoController iEquipoController;
	
	static final int CAPITAL_USUARIO = 10000;
	
	public UsuarioController()
	{
		this.usuarioDAO = new UsuarioDAOImpl();
		this.iEquipoController = new EquipoController();
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean existeUsuario(String login, String password) 
	{
		try
		{
			Usuario usuario = this.usuarioDAO.buscarUsuario(login, password);
			return (usuario != null);
		}
		catch (Throwable ex)
		{
			System.out.println("EXCEPCI�N: " + ex.getClass());
            return null;
		}
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean estaConectadoUsuario(String login)
	{
		try
		{
			return this.usuarioDAO.getEstaConectadoUsuario(login);
		}
		catch (Throwable ex)
		{
			System.out.println("EXCEPCI�N: " + ex.getClass());
            return null;
		}
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONObject ingresarUsuario(String login, String password, String mail, String equipo,
									  String pais, String localidad, String estadio) 
	{
		
		JSONObject jsonRegistrar = new JSONObject();
		
		if(this.usuarioDAO.existeUsuarioRegistrado(login))
		{
			try
			{
		   	 	jsonRegistrar.put("registrado", false);
		   	 	jsonRegistrar.put("mensaje", "ERROR. Existe usuario con ese nombre.");
	        }
	        catch(Exception ex)
			{
	            ex.printStackTrace();
	        }
		}
		else if(this.iEquipoController.existeEquipoRegistrado(equipo))
		{
			try
			{
		   	 	jsonRegistrar.put("registrado", false);
		   	 	jsonRegistrar.put("mensaje", "ERROR. Existe equipo con mismo nombre.");
	        }
	        catch(Exception ex)
			{
	            ex.printStackTrace();
	        }
		}
		else if(this.iEquipoController.existeEstadioRegistrado(estadio))
		{
			try
			{
		   	 	jsonRegistrar.put("registrado", false);
		   	 	jsonRegistrar.put("mensaje", "ERROR. Existe estadio con mismo nombre.");
	        }
	        catch(Exception ex)
			{
	            ex.printStackTrace();
	        }
		}
		else
		{
			Integer capitalIni = CAPITAL_USUARIO;
			Usuario u = new Usuario(login, password, mail, capitalIni);
			Equipo nuevoequipo = this.iEquipoController.crearEquipo(equipo, pais, localidad, estadio);
			u.setEquipo(nuevoequipo);
			this.usuarioDAO.insertarUsuario(u);
			try
			{
		   	 	jsonRegistrar.put("registrado", true);
		   	 	jsonRegistrar.put("mensaje","Usuario registrado.");
	        }
	        catch(Exception ex){
	            ex.printStackTrace();
	        }
		}
		return jsonRegistrar;
		
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void setearConectado(String login) 
	{
		this.usuarioDAO.setearConectado(login);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void setearDesconectado(String login) 
	{
		this.usuarioDAO.setearDesconectado(login);	
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean esAdmin(String login) 
	{
		return this.usuarioDAO.esAdministrador(login);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONArray obtenerDesconectados()
	{
		return this.usuarioDAO.obtenerDesconectados();
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean enviarChat(String emisor, String receptor, String mensaje)
	{
		return this.usuarioDAO.enviarChat(emisor, receptor, mensaje);		
	}

}
