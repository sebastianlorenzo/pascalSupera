package beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.codehaus.jettison.json.JSONException;

import com.google.gson.Gson;

import controladores.VistaWebController;
import tipos.DataListaNotificacion;
import tipos.DataNotificacion;

@ManagedBean
@RequestScoped
public class NotificacionBean {
	
	private String nombreUsr;
	private List<DataNotificacion> notificaciones;
	private int cant_notificaciones;

	@PostConstruct
	public void init() throws JSONException{
		VistaWebController vwc = new VistaWebController();
		FacesContext context   = FacesContext.getCurrentInstance();
		ELContext contextoEL   = context.getELContext( );
		Application apli       = context.getApplication( );		 
		ExpressionFactory ef   = apli.getExpressionFactory( );
		ValueExpression ve     = ef.createValueExpression(contextoEL, "#{loginBean}",LoginBean.class);
		LoginBean bean         = (LoginBean) ve.getValue(contextoEL);
		this.nombreUsr         = bean.getNombre();
		
		String respuesta = vwc.obtenerCantNotificaciones(this.nombreUsr);
		Gson g = new Gson();
		Integer cantNotificaciones = g.fromJson(respuesta, Integer.class);
		this.cant_notificaciones = cantNotificaciones;
	}
	
	public NotificacionBean(){	
		
	}

	public String getNombreUsr() {
		return nombreUsr;
	}

	public void setNombreUsr(String nombreUsr) {
		this.nombreUsr = nombreUsr;
	}

	public List<DataNotificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<DataNotificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	public int getCant_notificaciones() {
		return cant_notificaciones;
	}

	public void setCant_notificaciones(int cant_notificaciones) {
		this.cant_notificaciones = cant_notificaciones;
	}
	
	public List<DataNotificacion> cargarNotificaciones(){
		VistaWebController vwc = new VistaWebController();
		String respuesta = vwc.verNotificaciones(this.nombreUsr);
		Gson g = new Gson();
		DataListaNotificacion dln = g.fromJson(respuesta, DataListaNotificacion.class);
		this.notificaciones= dln.getListNotificaciones();
		respuesta = vwc.obtenerCantNotificaciones(this.nombreUsr);
		this.cant_notificaciones = g.fromJson(respuesta, Integer.class);
		
		return this.notificaciones;
	}
	
}
