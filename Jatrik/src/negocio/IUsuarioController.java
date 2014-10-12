package negocio;

import javax.ejb.Local;

@Local
public interface IUsuarioController {
	
	public Boolean existeUsuario(String login, String password);

}
