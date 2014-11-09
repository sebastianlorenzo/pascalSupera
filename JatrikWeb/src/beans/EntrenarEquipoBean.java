package beans;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controladores.VistaWebController;

@ManagedBean
@RequestScoped
public class EntrenarEquipoBean {

	private Integer porteria;
	private Integer defensivo;
	private Integer ofensivo;
	private Integer fisico;
	private String nombreEquipo;
	
	@PostConstruct
	void init() throws JSONException{
		FacesContext context = FacesContext.getCurrentInstance();
		ELContext contextoEL = context.getELContext( );
		Application apli  = context.getApplication( );		 
		ExpressionFactory ef = apli.getExpressionFactory( );
		ValueExpression ve = ef.createValueExpression(contextoEL, "#{loginBean}",LoginBean.class);
		LoginBean bean = (LoginBean) ve.getValue(contextoEL);
		this.nombreEquipo = bean.getNomEquipo();
		VistaWebController vwc = new VistaWebController();
		String resultado = vwc.obtenerEntrenamiento(this.nombreEquipo);
		JSONObject json = new JSONObject(resultado);
		this.porteria = Integer.parseInt(json.getString("Porteria"));
		this.defensivo = Integer.parseInt(json.getString("Defensivo"));
		this.ofensivo = Integer.parseInt(json.getString("Ofensivo"));
		this.fisico = Integer.parseInt(json.getString("Fisico"));
		
	}	

	public Integer getPorteria() {
		return porteria;
	}



	public void setPorteria(Integer porteria) {
		this.porteria = porteria;
	}



	public Integer getDefensivo() {
		return defensivo;
	}



	public void setDefensivo(Integer defensivo) {
		this.defensivo = defensivo;
	}



	public Integer getOfensivo() {
		return ofensivo;
	}



	public void setOfensivo(Integer ofensivo) {
		this.ofensivo = ofensivo;
	}



	public Integer getFisico() {
		return fisico;
	}



	public void setFisico(Integer fisico) {
		this.fisico = fisico;
	}



	public String getNombreEquipo() {
		return nombreEquipo;
	}



	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}



	public void confirmarEntrenamiento() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (this.defensivo==null)
			this.defensivo=0;
		if (this.fisico==null)
			this.fisico=0;
		if (this.ofensivo==null)
			this.ofensivo=0;
		if (this.porteria==null)
			this.porteria=0;
		Integer total = this.defensivo+this.fisico+this.ofensivo+this.porteria;
		if(total>10){
			context.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,"El total de estrellas no debe superar los 10 puntos","") );
		}
		else {
			VistaWebController vwc = new VistaWebController();
			vwc.modificarEntrenamiento(this.nombreEquipo,this.porteria.toString(), this.defensivo.toString(), this.fisico.toString(), this.ofensivo.toString());
			context.addMessage(null, new FacesMessage( "","El entrenamiento fue actualizado con éxito"));
		}
        
	}
	
}
