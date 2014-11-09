package beans;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.codehaus.jettison.json.JSONException;

import tipos.DataUsuario;

import com.google.gson.Gson;

import controladores.VistaWebController;

@ManagedBean
@RequestScoped
public class PerfilBean {
	
	private String nombreUsr;
	private String nombreEquipo;
	private String nombreEstadio;
	private Integer capital;
	private String localidad;
	
	@PostConstruct
	public void init() throws JSONException{
		VistaWebController vwc = new VistaWebController();
		FacesContext context = FacesContext.getCurrentInstance();
		ELContext contextoEL = context.getELContext( );
		Application apli  = context.getApplication( );		 
		ExpressionFactory ef = apli.getExpressionFactory( );
		ValueExpression ve = ef.createValueExpression(contextoEL, "#{loginBean}",LoginBean.class);
		LoginBean bean = (LoginBean) ve.getValue(contextoEL);
		this.nombreUsr = bean.getNombre();
		String respuesta = vwc.verPerfil(this.nombreUsr);
		Gson g = new Gson();
		DataUsuario du = g.fromJson(respuesta, DataUsuario.class);
		this.nombreEquipo = du.getNomEquipo();
		this.nombreEstadio= du.getNomEstadio();
		this.capital = du.getCapital();
		this.localidad = du.getUbicacion();	
		
	}
	
	public PerfilBean(){	
		
	}

	public String getNombreUsr() {
		return nombreUsr;
	}

	public void setNombreUsr(String nombreUsr) {
		this.nombreUsr = nombreUsr;
	}

	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}

	public String getNombreEstadio() {
		return nombreEstadio;
	}

	public void setNombreEstadio(String nombreEstadio) {
		this.nombreEstadio = nombreEstadio;
	}

	public Integer getCapital() {
		return capital;
	}

	public void setCapital(Integer capital) {
		this.capital = capital;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

}
