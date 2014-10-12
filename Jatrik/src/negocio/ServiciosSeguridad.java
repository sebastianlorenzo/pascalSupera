package negocio;

import javax.ejb.Local;

@Local
public interface ServiciosSeguridad {
	
	public Boolean existeUsuario(String login, String password);
	
}
