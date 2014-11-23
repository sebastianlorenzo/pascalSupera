package beans;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.validation.constraints.Size;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.primefaces.context.RequestContext;

import controladores.VistaWebController;

@ManagedBean
@RequestScoped
public class RegistroBean {
	
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
	private String googleKey="AIzaSyAqLuGawbfE7GsJbJH2ZJvb6Z02UoAhfIo";
    private String paisSeleccionado="";

	
    
	public RegistroBean() {
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
	
	public String getGoogleKey() {
		return googleKey;
	}

	public void setGoogleKey(String googleKey) {
		this.googleKey = googleKey;
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
	
	public void registarUsuario() throws JSONException{
		
		VistaWebController vwc = new VistaWebController();
		String pais = this.paisSeleccionado.replaceAll(",", "-");
		System.out.println(this.paisSeleccionado);
		String respuesta=vwc.registrarUsuario(nombre, pwd, mail, equipo,nomEstadio,pais);
		this.nombre="";
		this.pwd="";
		this.mail="";
		this.equipo="";
		this.nomEstadio="";
		this.nombre="";
		this.paisSeleccionado="";
		JSONObject json = new JSONObject(respuesta);
		String mensaje_box=json.getString("mensaje");
		String cabecera;
		Severity icono;
		if (json.getBoolean("ok")){
			cabecera="Bienvenido!";
			icono=FacesMessage.SEVERITY_INFO;
		}			
		else{
			cabecera="Lo siento.";
			icono=FacesMessage.SEVERITY_ERROR;
		}
			
		FacesMessage message = new FacesMessage(icono ,cabecera ,mensaje_box);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
		
	}


}
