package beans;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.validation.constraints.Future;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.primefaces.context.RequestContext;

import controladores.VistaWebController;

@ManagedBean
@RequestScoped
public class CrearCampeonatoBean {
	
	private String nombreCampeonato;
	private Integer cantidadEquipos;
	@Future(message="No puede ser una fecha pasada.")
	private Date fechaInicio;
	
	public CrearCampeonatoBean(){
		
	}
	
	public String getNombreCampeonato() {
		return nombreCampeonato;
	}
	public void setNombreCampeonato(String nombreCampeonato) {
		this.nombreCampeonato = nombreCampeonato;
	}
	public Integer getCantidadEquipos() {
		return cantidadEquipos;
	}
	public void setCantidadEquipos(Integer cantidadEquipos) {
		this.cantidadEquipos = cantidadEquipos;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public void crearCampeonato() throws JSONException{
		
		VistaWebController vwc = new VistaWebController();
		String respuesta=vwc.crearCampeonato(this.nombreCampeonato,this.cantidadEquipos,this.fechaInicio);
		JSONObject json = new JSONObject(respuesta);
		String mensaje=json.getString("mensaje");
		String cabecera;
		Severity icono;
		if (json.getBoolean("campeonato")){
			cabecera="Enhorabuena!";
			icono=FacesMessage.SEVERITY_INFO;
		}			
		else{
			cabecera="Lo siento.";
			icono=FacesMessage.SEVERITY_ERROR;
		}
		this.cantidadEquipos=null;
		this.fechaInicio=null;
		this.nombreCampeonato="";
		
		FacesMessage message = new FacesMessage(icono ,cabecera ,mensaje);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
		
	}
	
	}
	
	
