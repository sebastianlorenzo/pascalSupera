package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controladores.VistaWebController;



@ManagedBean
@SessionScoped
public class LoginBean {
	
	private String nombre;
	
	private String pwd;
	
	private boolean admin;
	
    public LoginBean() {
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
	
	public boolean estaLogueado(){
		return (this.nombre!=null);
	}
	
	
	
	public boolean isAdmin() {
		return admin;
	}


	public void setAdmin(boolean admin) {
		this.admin = admin;
	}


	public String salir() {
		
		  VistaWebController v = new VistaWebController();
		  if (v.logout(this.nombre)){
			  FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			  return "/index.xhtml?faces-redirect=true";
		  }
		  return "/paginas/home.xhtml?faces-redirect=true";
	}
	
	public String login() throws JSONException {
		
		VistaWebController v = new VistaWebController();
		String respuesta = v.login(nombre, pwd);		
		JSONObject json = new JSONObject(respuesta);
		if (json.getBoolean("login")){
			  
			if (json.getBoolean("es_admin")){
				this.admin=true;
				return "/paginas/administrador/home_admin.xhtml?faces-redirect=true";
			}
				
			else
				return "/paginas/usuario/home_user.xhtml?faces-redirect=true";
		  	 
		  }
		else 
			return "/index.xhtml?faces-redirect=true";
	}
	
	public String anotarmeCampeonato(){
		
		return "/paginas/usuario/anotarmeCampeonato_user.xhtml?faces-redirect=true";
		
	}
    
}
