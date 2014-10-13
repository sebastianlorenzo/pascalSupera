package negocio;

import javax.ejb.Local;

@Local
public interface IUsuarioController {
	
	public Boolean existeUsuario(String login, String password);
	
	public Boolean ingresarUsuario(String login, String password, String mail, String equipo,
			String pais, String localidad);

}
