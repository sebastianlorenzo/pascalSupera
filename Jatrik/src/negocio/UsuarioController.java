package negocio;

import javax.ejb.*;
import org.codehaus.jettison.json.JSONObject;
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
			Usuario usuario = this.usuarioDAO.buscarUsuario(login, password);
//aquí llamar para setear conectado true
			return (usuario != null);
		}
		catch (Throwable ex)
		{
			System.out.println("EXCEPCIÓN: " + ex.getClass());
            return null;
		}
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONObject ingresarUsuario(String login, String password, String mail,
			String equipo, String pais, String localidad, String estadio) {
		
		Boolean encontreUsuario = this.usuarioDAO.existeUsuarioRegistrado(login);
		Boolean encontreEquipo = this.iEquipoController.existeEquipoRegistrado(equipo);
		
		JSONObject jsonRegistrar = new JSONObject();
   	 			
		if(encontreUsuario){
			
			try{
		   	 	jsonRegistrar.put("registrado", false);
		   	 	jsonRegistrar.put("mensaje", "ERROR. Existe usuario con ese nombre.");
		        }
		        catch(Exception ex){
		            ex.printStackTrace();
		        }
		}else if(encontreEquipo){
			try{
		   	 	jsonRegistrar.put("registrado", false);
		   	 	jsonRegistrar.put("mensaje", "ERROR. Existe equipo con mismo nombre.");
		        }
		        catch(Exception ex){
		            ex.printStackTrace();
		        }
		}else{
			Usuario u = new Usuario(login, password, mail);
			Equipo nuevoequipo = this.iEquipoController.crearEquipo(equipo, pais, localidad, estadio);
			u.setEquipo(nuevoequipo);
			this.usuarioDAO.insertarUsuario(u);
			try{
		   	 	jsonRegistrar.put("registrado", true);
		   	 	jsonRegistrar.put("mensaje","Usuario registrado.");
		        }
		        catch(Exception ex){
		            ex.printStackTrace();
		        }
		}
		return jsonRegistrar;
		
	}

}
