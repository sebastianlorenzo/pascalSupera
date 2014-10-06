package beans;


import javax.faces.bean.ManagedBean;

import controladores.VistaWebController;



@ManagedBean
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
	
	public String checkLogin(){
		VistaWebController v = new VistaWebController();
		return (v.checkLogin(this.nombre, this.pwd));
	}
	
    
}
