package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.validation.constraints.Size;

import controladores.VistaWebController;

@ManagedBean
@RequestScoped
public class UsuarioRegistro {
	
	@Size(min=1, message="El nombre no puede ser vacio")
	private String nombre; //login
	@Size(min=1, message="La contraseña no puede ser vacia")
	private String pwd;
	@Size(min=1, message="El mail no puede ser vacio")
	private String mail;
	@Size(min=1, message="El nombre de equipo no puede ser vacio")
	private String equipo;
	@Size(min=1, message="El nombre del estadio no puede ser vacio")
	private String nomEstadio;
	
    private List<String> paises= new ArrayList<String>();
    
    private String paisSeleccionado;

	
    // postConstruct ejecuta la funcion luego de creado el bean , solo se ejecuta una vez
    @PostConstruct
    public void init() {
    	paises.add("Argentina");
    	paises.add("Brasil");
    	paises.add("Uruguay");
    }
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
	
	
	public List<String> getPaises() {
		return paises;
	}
	public void setPaises(List<String> paises) {
		this.paises = paises;
	}
	public String getPaisSeleccionado() {
		return paisSeleccionado;
	}
	public void setPaisSeleccionado(String paisSeleccionado) {
		this.paisSeleccionado = paisSeleccionado;
	}
	
	
	public String getNomEstadio() {
		return nomEstadio;
	}
	public void setNomEstadio(String nomEstadio) {
		this.nomEstadio = nomEstadio;
	}
	
	// en arg2 viene el string Nombre
	//Nombre va a ser el id que identifique al usuario en el sistema
	public void existeUsuario(FacesContext arg0, UIComponent arg1, Object arg2)
	         throws ValidatorException {
		String nombre = (String) arg2;
		VistaWebController vwc = new VistaWebController();
	      if (vwc.existeUsuario(nombre)) {
	    	  FacesMessage msg =new FacesMessage("Ya existe un usuario con ese nombre");
	    	  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	         throw new ValidatorException(msg);
	      }
	   }
	
	public String registarUsuario(){
		
		VistaWebController vwc = new VistaWebController();
		return vwc.registrarUsuario(nombre, pwd, mail, equipo,nomEstadio,paisSeleccionado);
		
	}


}
