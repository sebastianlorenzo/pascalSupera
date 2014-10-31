package beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

import com.google.gson.Gson;

import controladores.VistaWebController;
import dataTypes.DataCampeonato;
import dataTypes.DataListaCampeonato;

@ManagedBean
@RequestScoped
public class AnotarseCampeonatoBean {
	
	private List<DataCampeonato> campeonatos;
	private String campeonatoSeleccionado;
	
	public AnotarseCampeonatoBean (){
		VistaWebController vwc = new VistaWebController();
		Gson g = new Gson();
		DataListaCampeonato dlc = g.fromJson(vwc.listarCampeonatos(), DataListaCampeonato.class);
		this.campeonatos= dlc.getListCampeonatos();		
	}
	
	@PostConstruct
	public void init(){
	
	}

	public List <DataCampeonato> getDlc() {
		return campeonatos;
	}

	public void setDlc(List <DataCampeonato>dlc) {
		this.campeonatos = dlc;
	}
	
	
	public String getCampeonatoSeleccionado() {
		return campeonatoSeleccionado;
	}

	public void setCampeonatoSeleccionado(String campeonatoSeleccionado) {
		this.campeonatoSeleccionado = campeonatoSeleccionado;
	}

	public void onChange(TabChangeEvent event) {
		 String activeIndex = ((AccordionPanel) event.getComponent()).getActiveIndex();
	       
	        System.out.println("Active:" + activeIndex);
		
	}

	
	public String listarCampeonatos(){
		
		return "/paginas/usuario/anotarmeCampeonato_user.xhtml?faces-redirect=true";
		
	}
	
	public void anotarmeCampeonato() throws JSONException{
		
		String nomCampeonato=this.campeonatos.get(Integer.parseInt(this.campeonatoSeleccionado)).nomCampeonato;
		FacesContext context = FacesContext.getCurrentInstance();
		ELContext contextoEL = context.getELContext( );
		Application apli  = context.getApplication( );		 
		ExpressionFactory ef = apli.getExpressionFactory( );
		ValueExpression ve = ef.createValueExpression(contextoEL, "#{loginBean}",LoginBean.class);
		LoginBean bean = (LoginBean) ve.getValue(contextoEL);
		String nomUsr=bean.getNombre();
		VistaWebController v = new VistaWebController();
		String respuesta = v.anotarseCampeonato(nomUsr, nomCampeonato);	
		JSONObject json = new JSONObject(respuesta);
		String cabecera;
		Severity icono;
		String mensaje_box;
		if (json.getBoolean("inscripcion")){
			cabecera="Enhorabuena!";
			icono=FacesMessage.SEVERITY_INFO;
			mensaje_box="Su inscripción fue realizada con éxito";
		}			
		else{
			cabecera="Lo siento.";
			icono=FacesMessage.SEVERITY_ERROR;
			mensaje_box="Ocurrió un error al inscribirse";
		}
				
		FacesMessage message = new FacesMessage(icono ,cabecera ,mensaje_box);
	    RequestContext.getCurrentInstance().showMessageInDialog(message);	
		
	}
	
}
