package beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import controladores.VistaWebController;

@ManagedBean
@RequestScoped
public class UsuarioRegistro {
	
	private String nombre;
	private String pwd;
	private String mail;
	private String equipo;
	
	public UsuarioRegistro() {
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


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getEquipo() {
		return equipo;
	}


	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}
	
	// en arg2 viene el string Nombre
	//Nombre va a ser el id que identifique al usuario en el sistema
	public void existeUsuario(FacesContext arg0, UIComponent arg1, Object arg2)
	         throws ValidatorException {
		String nombre = (String) arg2;
		VistaWebController vwc = new VistaWebController();
	      if (vwc.existeUsuario(nombre)) {
	         throw new ValidatorException(new FacesMessage("Ya existe un usuario con ese nombre"));
	      }
	   }


}
