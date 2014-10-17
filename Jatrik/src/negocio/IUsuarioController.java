package negocio;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONObject;

@Local
public interface IUsuarioController {
	
	public Boolean existeUsuario(String login, String password);
	
	public JSONObject ingresarUsuario(String login, String password, String mail, String equipo,
			String pais, String localidad, String estadio);
	
	public void seteoConectado(String login);
	
	public void seteoDesconectado(String login);

}
