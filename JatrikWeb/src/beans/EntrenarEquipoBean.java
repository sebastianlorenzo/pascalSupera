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

import controladores.VistaWebController;

@ManagedBean
@RequestScoped
public class EntrenarEquipoBean {

	private Integer golero;
	private Integer defensa;
	private Integer mediocampo;
	private Integer ataque;
	
	@PostConstruct
	void init(){
		this.golero=0;
		this.defensa=0;
		this.mediocampo=0;
		this.ataque=0;
	}
	
	public Integer getGolero() {
		return golero;
	}
	public void setGolero(Integer golero) {
		this.golero = golero;
	}
	public Integer getDefensa() {
		return defensa;
	}
	public void setDefensa(Integer defensa) {
		this.defensa = defensa;
	}
	public Integer getMediocampo() {
		return mediocampo;
	}
	public void setMediocampo(Integer mediocampo) {
		this.mediocampo = mediocampo;
	}
	public Integer getAtaque() {
		return ataque;
	}
	public void setAtaque(Integer ataque) {
		this.ataque = ataque;
	}

	public void confirmarEntrenamiento() {
		FacesContext context = FacesContext.getCurrentInstance();
		Integer total = this.ataque+this.golero+this.defensa+this.mediocampo;
		if(total>10){
			context.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,"El total de estrellas no debe superar los 10 puntos","") );
		}
		else {
			ELContext contextoEL = context.getELContext( );
			Application apli  = context.getApplication( );		 
			ExpressionFactory ef = apli.getExpressionFactory( );
			ValueExpression ve = ef.createValueExpression(contextoEL, "#{loginBean}",LoginBean.class);
			LoginBean bean = (LoginBean) ve.getValue(contextoEL);
			VistaWebController vwc = new VistaWebController();
			vwc.entrenarEquipo(bean.getNomEquipo(),golero.toString(), defensa.toString(), mediocampo.toString(), ataque.toString());
			context.addMessage(null, new FacesMessage( "","El entrenamiento fue actualizado con éxito"));
		}
        
	}
	
}
