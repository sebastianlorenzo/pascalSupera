package beans;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.codehaus.jettison.json.JSONException;

import controladores.VistaWebController;



@ManagedBean
@SessionScoped
public class UsuarioLogin {
	
	private String nombre;
	
	private String pwd;
	
    public UsuarioLogin() {
        // TODO Auto-generated constructor stub
    }
    
    
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String salir() {
		  FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		  return "/index.xhtml?faces-redirect=true";
	}
	
	public String checkLogin() throws JSONException {
		VistaWebController v = new VistaWebController();
		  if (!v.checkLogin(nombre, pwd)) {
			  
			  return "/paginas/login.xhtml?faces-redirect=true";
			  /*FacesMessage msg = new FacesMessage("El nombre de Usuario o la contraseña no son válidos.");
		  	  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		  	  throw new ValidatorException(msg); */
		  	 
		  }
		  
		  return "/paginas/home.xhtml?faces-redirect=true";
	}
    
}
