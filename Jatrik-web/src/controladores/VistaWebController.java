package controladores;

public class VistaWebController {

	
	public String checkLogin (String nom, String pwd) {
		
		// aca hay que hacer un cliente rest y llamar al web services que implementa el check in
		if (nom.equals("juanfra") && pwd.equals("juanfra"))
			return "home";
		else
			return "login";
	}
	
	public boolean existeUsuario (String nom){
		
		if (nom.equals("juanfra2") || nom.equals("juanfra"))
			return true;
		else
			return false;
	}
	
}
